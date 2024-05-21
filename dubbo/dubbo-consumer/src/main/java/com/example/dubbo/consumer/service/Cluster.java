package com.example.dubbo.consumer.service;

/**
 * 容错模式
 * @author caimeng
 * @date 2024/5/21 15:13
 */
public interface Cluster {
    /**
     * 失败重试，默认值。
     * 当出现失败，重试其他服务器，默认重试两次，使用retries配置。
     * 一般用于读操作。
     */
    void failover();

    /**
     * 快速失败
     * 只发起一次调用，调用失败立即报错。
     * 通常用于写操作。
     */
    void failfast();

    /**
     * 失败安全。
     * 出现异常时，直接忽略，返回一个空结果。
     * 一般用于不重要的操作，比如日志。
     */
    void failsafe();

    /**
     * 失败自动恢复。
     * 后台记录失败的请求，定时重发。
     * 非常重要的操作。
     */
    void failback();

    /**
     * 并行调用多台服务器，只要有一个成功返回即可。
     */
    void forking();

    /**
     * 广播模式。
     * 调用所有接收者，逐个调用，任意一台报错则报错。
     * 同步要求较高时（一致性），可使用该模式。
     */
    void broadcast();
}
