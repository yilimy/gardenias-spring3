package com.example.mybatis;

import com.example.mybatis.mapper.UserMapper;
import com.example.mybatis.po.User;
import com.example.mybatis.proxy.MapperProxyFactory;

import java.util.List;

/**
 * <a href="https://www.bilibili.com/video/BV15e4y1d7na/?p=1&spm_id_from=pageDriver&vd_source=ae63e735dcccc0734ec3b3b043a159f9">图灵课堂周瑜手写Mybatis源码</a>
 * @author caimeng
 * @date 2024/5/10 17:11
 */
public class MybatisApplicationTest {

    public static void main(String[] args) {
        UserMapper userMapper = MapperProxyFactory.getMapper(UserMapper.class);
        List<User> userList = userMapper.getUser("Jack");
        System.out.println(userList);
    }
}
