package com.example.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.domain.LoginUser;
import com.example.utils.JwtUtil;
import com.example.utils.RedisCache;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @author liuga
 * @date 2022/10/09 15:01
 * Description:认证过滤器
 * <p>
 * 我们需要自定义一个过滤器，这个过滤器会去获取请求头中的 token，
 * 对 token 进行解析取出其中的 userid。
 * 使用 userid 去 redis 中获取对应的 LoginUser 对象。
 * <p>
 * 然后封装 Authentication 对象存入 SecurityContextHolder
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取token
        String token = request.getHeader("token");
        if (!StringUtils.hasText(token)) {
            //没有token 放行 由其他过滤器判断其状态
            filterChain.doFilter(request, response);
            return;
        }
        //存在token 则解析token
        String userid;
        try {
            Claims claims = JwtUtil.parseJWT(token);
            //获取存储的 id
            userid = claims.getSubject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("token非法");
        }
        //从redis中获取用户信息
        String redisKey = "login:" + userid;
        LoginUser loginUser = redisCache.getCacheObject(redisKey);
        if (Objects.isNull(loginUser)) {
            throw new RuntimeException("用户未登录");
        }
        //存入SecurityContextHolder
        //TODO 获取权限信息封装到 Authentication中  loginUser.getAuthorities()
        //标识是否已认证状态 用三个参数的构造,认证的请求不再进行登录
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        //放行
        filterChain.doFilter(request, response);
    }
}