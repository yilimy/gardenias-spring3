package com.example.dubbo.consumer.service.impl;

import com.alibaba.dubbo.cache.CacheFactory;
import com.alibaba.dubbo.config.AbstractMethodConfig;
import com.alibaba.dubbo.config.annotation.Reference;
import com.example.dubbo.consumer.service.IUserService;
import com.example.dubbo.inter.bean.CommonResult;
import com.example.dubbo.inter.bean.Orders;
import com.example.dubbo.inter.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;

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
     * <p>
     *     loadbalance 负载均衡{@link com.alibaba.dubbo.rpc.cluster.LoadBalance}
     *      random, 默认，随机负载均衡
     *      leastactive, 最少活跃调用数，相同活跃数随机
     *      roundrobin, 轮询
     *      consistenthash, 一致性hash负载均衡
     * <p>
     *     cluster 容错模式
     *     {@link com.alibaba.dubbo.rpc.cluster.Cluster}
     *     {@link com.example.dubbo.consumer.service.Cluster}
     * <p>
     *     服务降级
     *     {@link com.alibaba.dubbo.config.AbstractMethodConfig#mock}
     *     {@link com.alibaba.dubbo.rpc.support.MockInvoker#normallizeMock(String)}
     *     {@link com.alibaba.dubbo.rpc.support.MockInvoker#parseMockValue(String, Type[])}
     *     mock=force:return null  该方法强制返回null，不发起远程调用
     *     mock=fail:return null  该方法调用失败时返回null，不抛异常
     * <p>
     *     cache 缓存
     *     {@link com.alibaba.dubbo.config.AbstractMethodConfig#cache}
     *     {@link com.alibaba.dubbo.cache.CacheFactory}
     *     Dubbo提供了三种缓存机制
     *      lru             基于最近最少使用原则删除多余缓存，保持最热的数据被换缓存。
     *      jcache          当前线程缓存，比如一个页面渲染，用到很多portal，每个portal都要去查用户信息，通过线程缓存，可以减少这种多余的访问。
     *      threadlocal     与JSR107集成，可以桥接各种很缓存实现。
     */
//    @Reference(timeout = 2000, version = "v1.*.0")
//    @Reference(timeout = 2000, version = "v1.0.0")
    @Reference(timeout = 2000, version = "*", loadbalance = "random", cluster = "failover")
    private IOrderService orderService;
    @Override
    public CommonResult<Orders> findByUserId(Long userId) {
        log.info("根据用户id[{}]查询订单信息", userId);
        return orderService.findOrdersByUserId(userId);
    }
}
