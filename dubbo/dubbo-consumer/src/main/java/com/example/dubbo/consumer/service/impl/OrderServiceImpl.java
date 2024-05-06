package com.example.dubbo.consumer.service.impl;

import com.example.dubbo.inter.bean.UserAddress;
import com.example.dubbo.inter.service.OrderService;
import com.example.dubbo.inter.service.UserService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author caimeng
 * @date 2024/4/29 10:54
 */
@Slf4j
public class OrderServiceImpl implements OrderService {
    UserService userService;

    @Override
    public void initOrder(String userId) {
        // 1. 查询用户收货地址
        List<UserAddress> userAddressList = userService.getUserAddressList(userId);
        log.info("查询用户收货地址:{}", userAddressList);
    }
}
