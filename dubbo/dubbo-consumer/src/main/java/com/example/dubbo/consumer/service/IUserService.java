package com.example.dubbo.consumer.service;

import com.example.dubbo.inter.bean.CommonResult;
import com.example.dubbo.inter.bean.Orders;

/**
 * @author caimeng
 * @date 2024/5/16 14:16
 */
public interface IUserService {
    /**
     * 根据用户ID查询用户订单详情
     * @param userId 用户id
     * @return 用户订单详情
     */
    CommonResult<Orders> findByUserId(Long userId);
}
