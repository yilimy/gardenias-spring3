package com.example.dubbo.consumer.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.example.dubbo.consumer.service.IUserService;
import com.example.dubbo.inter.bean.CommonResult;
import com.example.dubbo.inter.bean.Orders;
import com.example.dubbo.inter.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author caimeng
 * @date 2024/5/16 14:18
 */
@Slf4j
@Service
public class IUserServiceImpl implements IUserService {
    /**
     * 1. 从zookeeper注册中心中获取IOrderService访问的url
     * 2. 进行远程调用，使用RPC
     * 3. 将结果封装成代理对象，交给变量进行赋值
     * timeout 超时时间：2秒，优先级高于 @Service，会覆盖该请求的超时时间
     * version=* 匹配所有版本, version=v1.0.0表示指定为特定版本,不支持version=v1.*.0这样的匹配
     */
//    @Reference(timeout = 2000, version = "v1.*.0")
//    @Reference(timeout = 2000, version = "v1.0.0")
    @Reference(timeout = 2000, version = "*")
    private IOrderService orderService;
    @Override
    public CommonResult<Orders> findByUserId(Long userId) {
        log.info("根据用户id[{}]查询订单信息", userId);
        return orderService.findOrdersByUserId(userId);
    }
}
