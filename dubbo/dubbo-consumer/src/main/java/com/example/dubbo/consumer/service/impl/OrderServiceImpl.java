package com.example.dubbo.consumer.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.example.dubbo.inter.service.OrderService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author caimeng
 * @date 2024/4/29 10:54
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Override
    public void initOrder(String userId) {
        log.info("测试 initOrder .");
    }
}
