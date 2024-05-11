package com.example.mybatis.mapper;

import com.example.mybatis.annotation.Param;
import com.example.mybatis.annotation.Select;
import com.example.mybatis.po.User;

import java.util.List;

/**
 * @author caimeng
 * @date 2024/5/10 17:12
 */
public interface UserMapper {
    @Select("select * from user where name = #{name}")
    List<User> getUser(@Param("name") String name);

    @Select("select * from user where name = #{name} and age = #{age}")
    List<User> getUserByNameAndAge(@Param("name") String name, @Param("age") Integer age);

    User getUserById(Integer id);
}
