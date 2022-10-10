package com.example.service;

import com.example.domain.ResponseResult;
import com.example.domain.User;

/**
 * @author liuga
 * @date 2022/10/09 12:02
 * Description:
 */
public interface LoginService {
    /**
     *
     * @param user
     * @return
     */
    ResponseResult login(User user);
    ResponseResult logout();
}
