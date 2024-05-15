package com.example.dubbo.consumer.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.example.dubbo.consumer.service.DubboDemoService;
import com.example.dubbo.inter.bean.CommonResult;
import com.example.dubbo.inter.bean.Orders;
import com.example.dubbo.inter.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author caimeng
 * @date 2024/5/15 18:06
 */
@Slf4j
@Service
public class DubboDemoServiceImpl implements DubboDemoService {
    @Reference
    IOrderService orderService;
    @Override
    public void initOrder(String userId) {
        Orders orders = new Orders().setId(1L).setUserId(2L);
        CommonResult<?> creatorResult = orderService.createOrders(orders);
        log.info("creatorResult = {}", creatorResult);
        CommonResult<Orders> ordersResult = orderService.findOrdersByUserId(2L);
        log.info("ordersResult = {}", ordersResult);
    }
}
