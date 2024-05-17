package com.example.dubbo.consumer.service;


/**
 * Dubbo 负载均衡
 * @author caimeng
 * @date 2024/5/16 17:28
 */
public class LoadBalanceService {

    /**
     * 随机负载均衡 {@link com.alibaba.dubbo.rpc.cluster.loadbalance.RandomLoadBalance}
     * 轮询负载均衡 {@link com.alibaba.dubbo.rpc.cluster.loadbalance.RoundRobinLoadBalance}
     * 最少活跃调用数负载均衡 {@link com.alibaba.dubbo.rpc.cluster.loadbalance.LeastActiveLoadBalance}
     * 一致性哈希负载均衡 {@link com.alibaba.dubbo.rpc.cluster.loadbalance.ConsistentHashLoadBalance}
     *
     * 一致性哈希: 相同的参数的请求总是落在同一台机器上
     */
    public void balance() {}
}
