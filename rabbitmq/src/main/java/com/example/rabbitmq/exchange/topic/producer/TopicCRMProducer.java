package com.example.rabbitmq.exchange.topic.producer;

import com.rabbitmq.client.MessageProperties;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

/**
 * 主题模式的生产者
 * 相比于 {@link TopicERPProducer},只是生产者发布时定义的 routingKey 不一样 (ROUTING_KEY_CRM)
 * @author caimeng
 * @date 2024/8/13 15:38
 */
@Slf4j
public class TopicCRMProducer extends TopicERPProducer {

    public static void main(String[] args) {
        new TopicCRMProducer().firstPublish();
    }

    @SneakyThrows
    protected void produce() {
        // 获取当前的发送时间
        long start = System.currentTimeMillis();
        CompletableFuture<Void>[] completableFutures = Stream.iterate(0, n -> n + 1)
                .limit(100)
                .map(n -> CompletableFuture.runAsync(() -> {
                    String msg = "【沐言科技 - CRM管理系统 - " + n +"】[topic] www.yootk.com";
                    try {
                        /*
                         * 相比于 TopicERPProducer， 变更了 routingKey
                         */
                        channel.basicPublish(EXCHANGE_TOPIC, ROUTING_KEY_CRM, MessageProperties.PERSISTENT_TEXT_PLAIN, msg.getBytes(StandardCharsets.UTF_8));
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
