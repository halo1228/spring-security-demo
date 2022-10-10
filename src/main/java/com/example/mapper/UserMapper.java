package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.domain.User;
import org.springframework.stereotype.Repository;

/**
 * @author liuga
 * @date 2022/10/09 10:56
 * Description:
 */
@Repository
public interface UserMapper extends BaseMapper<User> {
}