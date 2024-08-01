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
    // 消息组件的通道
    protected Channel channel;
    protected Connection connection;

    @SneakyThrows
    protected void init() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(HOST);
        factory.setPort(PORT);
        factory.setUsername(USERNAME);
        factory.setPassword(PASSWORD);
        // 创建连接
        connection = factory.newConnection();
        // 连接创建之后，要去获取通道
        channel = connection.createChannel();
        // 所有的消息的内容都保存在队列中，但是现阶段RabbitMQ没有提供队列。
        // 声明一个队列
        channel.queueDeclare(QUEUE_NAME, false, false, true, null);
    }

    @SneakyThrows
    protected void destroy() {
        // 关闭通道
        channel.close();
        // 关闭连接
        connection.close();
    }
}
