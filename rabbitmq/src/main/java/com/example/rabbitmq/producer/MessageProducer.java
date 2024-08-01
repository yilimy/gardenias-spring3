package com.example.rabbitmq.producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

/**
 * 消息生产者
 * @author caimeng
 * @date 2024/7/31 16:19
 */
@Slf4j
public class MessageProducer {
    // 队列名称
    private static final String QUEUE_NAME = "yootk.queue.msg";
    // 主机地址
    private static final String HOST = "localhost";
    // 服务端口
    private static final int PORT = 5672;
    // 用户名
    private static final String USERNAME = "yootk";
    // 密码
    private static final String PASSWORD = "hello";

    @SneakyThrows
    public void firstPublish() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(HOST);
        factory.setPort(PORT);
        factory.setUsername(USERNAME);
        factory.setPassword(PASSWORD);
        // 创建连接
        Connection connection = factory.newConnection();
        // 连接创建之后，要去获取通道
        Channel channel = connection.createChannel();
        // 所有的消息的内容都保存在队列中，但是现阶段RabbitMQ没有提供队列。
        // 声明一个队列
        channel.queueDeclare(QUEUE_NAME, false, false, true, null);
        // 获取当前的发送时间
        long start = System.currentTimeMillis();
        CompletableFuture<Void>[] completableFutures = Stream.iterate(0, n -> n + 1)
                .limit(100)
                .map(n -> CompletableFuture.runAsync(() -> {
                    String msg = "【沐言科技 - " + n +"】www.yootk.com";
                    try {
                        // 通过通道往指定队列中发送消息
                        channel.basicPublish("", QUEUE_NAME, null, msg.getBytes(StandardCharsets.UTF_8));
                    } catch (IOException e) {
                        log.error("发布失败: " + msg, e);
                    }
                })).<CompletableFuture<Void>>toArray(CompletableFuture[]::new);
        CompletableFuture.allOf(completableFutures).join();
        // 获取结束的发送时间
        long end = System.currentTimeMillis();
        System.out.println("【消息发送完毕】消息发送的耗时时间" + (end - start) + "ms");
        // 关闭通道
        channel.close();
        // 关闭连接
        connection.close();
    }


}
