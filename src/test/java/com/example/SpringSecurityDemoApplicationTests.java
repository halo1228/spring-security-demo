package com.example;

import com.example.domain.User;
import com.example.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootTest
class SpringSecurityDemoApplicationTests {

    @Autowired
    private UserMapper userMapper;
    /**
     * 加密
     */
    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    public void testUserMapper() {
        List<User> users = userMapper.selectList(null);
        System.out.println(users);
    }

    @Test
    void TestBCryptPasswordEncoder() {
        // BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encode = passwordEncoder.encode("1234");
        //每次加密的结果都是不同的
        System.out.println(encode);
        //判断值和加密后的值是否一致
        boolean matches = passwordEncoder.matches("1234", encode);
        System.out.println(matches);

    }
}
