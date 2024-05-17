package com.example.dubbo.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.example.dubbo.inter.bean.CommonResult;
import com.example.dubbo.inter.bean.Orders;
import com.example.dubbo.inter.service.IOrderService;
import org.springframework.beans.factory.annotation.Value;

/**
 * 订单的业务层
 * dubbo的service,将该类对外发布,将访问地址的ip端口和路径注册到注册中心
 * <p>
 *     timeout: 优先级低于 @Reference，但是建议定义在 @Service 中，因为 @Service 中知道业务压力
 *     单位是毫秒, timeout=3000 ==> 超时时间：3秒
 * <p>
 *     retries: 默认重试两次，配置 retries=2 后再重试2次
 * <p>
 *     version: 如果@Reference不配置，会出现报错，500
 *     该配置会在 <a href="http://localhost:38080/#/service?filter=%2a&pattern=service">服务查询</a> 面板中,显示版本号,和其对应的接口
 * @author caimeng
 * @date 2024/5/15 15:23
 */
@Service(timeout = 3000, retries = 2, version = "v1.0.0")
//@Service(timeout = 3000, retries = 2, version = "v1.1.0")
//@Service(timeout = 3000, retries = 2, version = "v2.0.0")
public class IOrderServiceImpl implements IOrderService {
    @Value("${demo.data.version:}")
    private String version;

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
                .setPayMethod(1)
                .setVersion(version);
        CommonResult<Orders> commonResult = new CommonResult<>();
        commonResult.setCode(200).setMessage("查询成功").setData(orders);
        return commonResult;
    }
}
