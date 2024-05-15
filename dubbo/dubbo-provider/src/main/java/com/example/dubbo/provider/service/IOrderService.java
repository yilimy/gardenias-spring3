package com.example.dubbo.provider.service;

import com.example.dubbo.inter.bean.CommonResult;
import com.example.dubbo.inter.bean.Orders;

/**
 * 订单接口
 * 该接口为对外调用接口(RPC调用),需要封装统一的返回数据类型
 * @author caimeng
 * @date 2024/5/15 15:18
 */
public interface IOrderService {

    /**
     * 创建订单
     * @param orders 订单
     */
    CommonResult<?> createOrders(Orders orders);

    /**
     * 根据用户ID查询订单详情
     * @param userId 用户ID
     * @return 订单详情
     */
    CommonResult<Orders> findOrdersByUserId(Long userId);
}
