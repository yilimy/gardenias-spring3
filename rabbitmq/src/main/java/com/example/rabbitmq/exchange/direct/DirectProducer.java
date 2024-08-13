package com.example.rabbitmq.exchange.direct;

import com.example.rabbitmq.RabbitMQServiceAbs;
import com.example.rabbitmq.exchange.fanout.MessageFanoutProducer;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

/**
 * 直连模式的生产者
 * <p>
 *     相比于 {@link MessageFanoutProducer} fanout的生产者，直连模式的生产者在声明exchange的时候
 *     {@link Channel#exchangeDeclare(String, String)}
 *     以及发布的时候
 *     {@link Channel#basicPublish(String, String, AMQP.BasicProperties, byte[])}
 *     要配置直连的 EXCHANGE_DIRECT 和 ROUTING_KEY
 * @author caimeng
 * @date 2024/8/13 11:35
 */
@Slf4j
public class DirectProducer extends RabbitMQServiceAbs {

    public static void main(String[] args) {
        new DirectProducer().firstPublish();
    }

    public void firstPublish() {
        // 这里定义exchange的名字，和类型 (指direct)
        initExchangeWithDirect();
        produce();
        destroy();
    }

    @SneakyThrows
    protected void produce() {
        // 获取当前的发送时间
        long start = System.currentTimeMillis();
        CompletableFuture<Void>[] completableFutures = Stream.iterate(0, n -> n + 1)
                .limit(100)
                .map(n -> CompletableFuture.runAsync(() -> {
                    String msg = "【沐言科技 - " + n +"】[direct] www.yootk.com";
                    try {
                        /*
                         * 直连模式，要设置routingKey
                         */
                        channel.basicPublish(EXCHANGE_DIRECT, ROUTING_KEY, MessageProperties.PERSISTENT_TEXT_PLAIN, msg.getBytes(StandardCharsets.UTF_8));
                    } catch (IOException e) {
                        log.error("发布失败: " + msg, e);
                    }
                })).<CompletableFuture<Void>>toArray(CompletableFuture[]::new);
        CompletableFuture.allOf(completableFutures).join();
        // 获取结束的发送时间
        long end = System.currentTimeMillis();
        System.out.println("【消息发送完毕】消息发送的耗时时间" + (end - start) + "ms");
    }
}
