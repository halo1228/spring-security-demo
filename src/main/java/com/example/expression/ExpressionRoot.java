package com.example.expression;

import com.example.domain.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;

/**
 * @author liuga
 * @date 2022/10/10 11:49
 * Description: 自定义自己的权限校验方法，在 @PreAuthorize 注解中使用我们的方法。
 */
@Component("ex")
public class ExpressionRoot {

    public boolean hasAuthority(String authority) {
        //获取当前用户的权限
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();

        return loginUser.getPermissions().contains(authority);

    }
}
