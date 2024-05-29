package com.example.mybatis.spring.service;

import com.example.mybatis.spring.mapper.OrderMapper;
import com.example.mybatis.spring.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author caimeng
 * @date 2024/3/26 18:55
 */
@Component
public class UserService {
    @Autowired(required = false)
    private UserMapper userMapper;

    @Autowired(required = false)
    private OrderMapper orderMapper;

    public void test() {
        String user = userMapper.getUser();
        System.out.println(user);
    }

    public void testOrder() {
        String user = orderMapper.getOrderId();
        System.out.println(user);
    }
}
