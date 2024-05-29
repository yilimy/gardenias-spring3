package com.example.mybatis.spring.mapper;

import org.apache.ibatis.annotations.Select;

/**
 * @author caimeng
 * @date 2024/3/26 18:53
 */
public interface UserMapper {
    @Select("select 'LiLei'")
    String getUser();
}
