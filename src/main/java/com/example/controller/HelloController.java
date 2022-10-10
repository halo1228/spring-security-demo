package com.example.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liuga
 * @date 2022/10/09 9:31
 * Description:
 */
@RestController
public class HelloController {
    /**
     * 在 SPEL 表达式中使用 @ex 相当于获取容器中 bean 的名字未 ex 的对象。然后再调用这个对象的 hasAuthority 方法
     *
     * @return
     */
    @RequestMapping("/hello")
    @PreAuthorize("@ex.hasAuthority('system:hello:list')")
    public String hello() {
        return "hello";
    }

    @RequestMapping("/test")
    @PreAuthorize("hasAuthority('system:test:list')")
    public String test() {
        return "test";
    }
}
