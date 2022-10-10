package com.example.controller;

import com.example.domain.ResponseResult;
import com.example.domain.User;
import com.example.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liuga
 * @date 2022/10/09 11:45
 * Description:
 */
@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody User user){
        return loginService.login(user);
    }

    @GetMapping("/user/logout")
    public ResponseResult logout(){
        return loginService.logout();
    }
    //23
    // 认证配置详解
}