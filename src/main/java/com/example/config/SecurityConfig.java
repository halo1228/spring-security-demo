package com.example.config;

import com.example.filter.JwtAuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author liuga
 * @date 2022/10/09 11:44
 * Description:
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)//开启权限认证
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    /**
     * 实际项目中我们不会把密码明文存储在数据库中。
     * <p>
     * 默认使用的 PasswordEncoder 要求数据库中的密码格式为：{id}password 。
     * 它会根据 id 去判断密码的加密方式。但是我们一般不会采用这种方式。
     * 所以就需要替换 PasswordEncoder。
     * <p>
     * 我们一般使用 SpringSecurity 为我们提供的 BCryptPasswordEncoder。
     * <p>
     * 我们只需要使用把 BCryptPasswordEncoder 对象注入 Spring 容器中，
     * SpringSecurity 就会使用该 PasswordEncoder 来进行密码校验。
     * <p>
     * 我们可以定义一个 SpringSecurity 的配置类，
     * SpringSecurity 要求这个配置类要继承 WebSecurityConfigurerAdapter。
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 配置一些认证的规则
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //关闭csrf
                .csrf().disable()
                //不通过Session获取SecurityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // permitAll 无论登录还是未登录 都能访问
                .antMatchers("/hello").permitAll()
                // 对于登录接口 允许匿名访问
                .antMatchers("/user/login").anonymous()
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated();
        //把token校验过滤器添加到过滤器链中
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        //自定义异常 AuthenticationEntryPoint 和 AccessDeniedHandler 配置给 SpringSecurity
        http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint).
                accessDeniedHandler(accessDeniedHandler);

        //允许跨域
        http.cors();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        //存储了 用户信息
        return super.authenticationManagerBean();
    }
}
