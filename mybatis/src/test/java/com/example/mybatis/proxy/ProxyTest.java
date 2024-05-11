package com.example.mybatis.proxy;

import com.example.mybatis.mapper.UserMapper;
import com.example.mybatis.po.User;
import org.junit.Test;

import java.util.List;

/**
 * @author caimeng
 * @date 2024/5/10 18:40
 */
public class ProxyTest {

    @Test
    public void mapperProxyTest() {
        UserMapper userMapper = MapperProxyFactory.getMapper(UserMapper.class);
        List<User> userList = userMapper.getUser("Jack");
        System.out.println(userList);
        userList = userMapper.getUserByNameAndAge("Jack", 18);
        System.out.println(userList);
    }
}
