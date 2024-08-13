package com.example.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.SneakyThrows;

/**
 * @author caimeng
 * @date 2024/8/1 13:51
 */
public abstract class RabbitMQServiceAbs {
    // 队列名称
    protected static final String QUEUE_NAME = "yootk.queue.msg";
    // 主机地址
    protected static final String HOST = "localhost";
    // 服务端口
    protected static final int PORT = 5672;
    // 用户名
    protected static final String USERNAME = "yootk";
    // 密码
    protected static final String PASSWORD = "hello";
    // 虚拟主机的名称
    protected static final String VHOST =  "MuyanVHost";
    protected static final String EXCHANGE_NAME = "yootk.exchange.fanout";
    // 路由的key
    protected static final String ROUTING_KEY = "yootk.routing.key";
    // 与路由key一同配置的exchange
    protected static final String EXCHANGE_DIRECT = "yootk.exchange.direct";
    // 消息组件的通道
    protected Channel channel;
    protected Connection connection;

    /**
     * 使用普通模式
     */
    @SneakyThrows
    protected void init() {
        initConnAndChannel();
        // 所有的消息的内容都保存在队列中，但是现阶段RabbitMQ没有提供队列。
        // 声明一个队列
        channel.queueDeclare(QUEUE_NAME, false, false, true, null);
    }

    /**
     * 使用持久化队列的方式进行发布
     * durable=true
     */
    @SneakyThrows
    protected void initPersistence() {
        initConnAndChannel();
        // 在声明队列的时候，指定为持久化队列
        channel.queueDeclare(QUEUE_NAME, true, false, true, null);
    }

    /**
     * 指定虚拟主机
     */
    @SneakyThrows
    protected void initWithHost() {
        initConnAndChannelWithHost();
        // 在声明队列的时候，指定为持久化队列
        channel.queueDeclare(QUEUE_NAME, false, false, true, null);
    }

    @SneakyThrows
    protected void initWithHostAndExchange() {
        initConnAndChannelWithHost();
        /*
         * 创建Exchange
         * 使用了exchange之后，不再使用队列了 (queueDeclare)
         */
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
    }

    @SneakyThrows
    protected void initExchangeWithDirect() {
        initConnAndChannelWithHost();
        /*
         * 创建Exchange
         * 这里要使用直连模式
         */
        channel.exchangeDeclare(EXCHANGE_DIRECT, "direct");
    }

    @SneakyThrows
    private void initConnAndChannel() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(HOST);
        factory.setPort(PORT);
        factory.setUsername(USERNAME);
        factory.setPassword(PASSWORD);
        // 创建连接
        connection = factory.newConnection();
        // 连接创建之后，要去获取通道
        channel = connection.createChannel();
    }

    /**
     * 含有虚拟主机的初始化配置
     */
    @SneakyThrows
    protected void initConnAndChannelWithHost() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(HOST);
        factory.setPort(PORT);
        factory.setUsername(USERNAME);
        factory.setPassword(PASSWORD);
        // 指定虚拟主机
        factory.setVirtualHost(VHOST);
        connection = factory.newConnection();
        channel = connection.createChannel();
    }

    @SneakyThrows
    protected void destroy() {
        // 关闭通道
        channel.close();
        // 关闭连接
        connection.close();
    }
}
