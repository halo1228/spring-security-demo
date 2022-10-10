package com.example.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.sun.xml.internal.ws.developer.Serialization;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author liuga
 * @date 2022/10/09 11:26
 * Description:存储登录用户信息及权限信息
 * 因为 UserDetailsService 方法的返回值是 UserDetails 类型，所以需要定义一个类，实现该接口，把用户信息封装在其中。
 */
@Data
@NoArgsConstructor
public class LoginUser implements UserDetails {

    private User user;

    /**
     * 存储权限信息
     */
    private List<String> permissions;


    public LoginUser(User user, List<String> permissions) {
        this.user = user;
        this.permissions = permissions;
    }

    public LoginUser(User user) {
        this.user = user;
    }

    /**
     * 存储SpringSecurity所需要的权限信息的集合
     *
     * @JSONField(serialize = false) 此成员变量不会序列化到redis中
     */
    @JSONField(serialize = false)
    private List<GrantedAuthority> authorities;

    /**
     * 该方法获取权限信息 重写该方法
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (authorities != null) {
            return authorities;
        }
        //把permissions中字符串类型的权限信息转换成GrantedAuthority对象存入authorities中
        authorities = permissions.stream().
                map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

