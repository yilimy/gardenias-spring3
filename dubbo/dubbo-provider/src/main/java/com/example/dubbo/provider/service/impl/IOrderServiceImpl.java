package com.example.dubbo.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.example.dubbo.inter.bean.CommonResult;
import com.example.dubbo.inter.bean.Orders;
import com.example.dubbo.inter.service.IOrderService;

/**
 * 订单的业务层
 * @author caimeng
 * @date 2024/5/15 15:23
 */
// dubbo的service,将该类对外发布,将访问地址的ip端口和路径注册到注册中心
@Service
public class IOrderServiceImpl implements IOrderService {

    // mapper

    @Override
    public CommonResult<?> createOrders(Orders orders) {
        CommonResult<?> commonResult = new CommonResult<>();
        commonResult.setCode(200).setMessage("订单创建成功");
        return commonResult;
    }

    @Override
    public CommonResult<Orders> findOrdersByUserId(Long userId) {
        Orders orders = new Orders().setId(1L)
                .setUserId(2L)
                .setPriceTotal(12.0d)
                .setMobile("18888888888")
                .setAddress("北京")
                .setPayMethod(1);
        CommonResult<Orders> commonResult = new CommonResult<>();
        commonResult.setCode(200).setMessage("查询成功").setData(orders);
        return commonResult;
    }
}
