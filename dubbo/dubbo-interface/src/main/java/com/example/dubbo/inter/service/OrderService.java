package com.example.dubbo.inter.service;

/**
 * 订单服务
 * @author caimeng
 * @date 2024/4/29 10:53
 */
public interface OrderService {

    /**
     * 根据用户ID初始化订单
     * @param userId 用户ID
     */
    void initOrder(String userId);
}
