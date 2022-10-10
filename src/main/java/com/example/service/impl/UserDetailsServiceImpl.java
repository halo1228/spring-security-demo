package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.domain.LoginUser;
import com.example.domain.User;
import com.example.mapper.MenuMapper;
import com.example.mapper.UserMapper;
import io.netty.util.internal.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author liuga
 * @date 2022/10/09 11:02
 * Description:
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    MenuMapper menuMapper;

    /**
     * @param username 标识需要其数据的用户的用户id。
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //查询用户信息
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUserName, username);
        User user = userMapper.selectOne(wrapper);
        //如果没有查询到用户就爆出异常
        if (Objects.isNull(user)) {
            //抛出的异常信息会被 ExceptionTranslationFilter 捕捉到
            throw new RuntimeException("用户不存在!!");
        }
        // TODO 查询对应的权限信息
        // 权限集合 查询
        //List<String> list = new ArrayList<>(Arrays.asList("test", "admin"));
        List<String> list = menuMapper.selectPermsByUserId(user.getId());
        //把数据封装成 UserDetails
        return new LoginUser(user, list);
    }
}
