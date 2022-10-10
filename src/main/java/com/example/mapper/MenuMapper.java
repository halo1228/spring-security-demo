package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.domain.Menu;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author liuga
 * @date 2022/10/10 10:28
 * Description:
 */
@Repository
public interface MenuMapper extends BaseMapper<Menu> {
    /**
     * 查询权限
     * @param id
     * @return
     */
    List<String> selectPermsByUserId(Long id);

}

