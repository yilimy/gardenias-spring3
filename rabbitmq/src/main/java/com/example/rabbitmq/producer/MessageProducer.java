package com.example.rabbitmq.producer;

import com.example.rabbitmq.RabbitMQServiceAbs;
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
public class MessageProducer extends RabbitMQServiceAbs {

    @SneakyThrows
    public void firstPublish() {
        init();
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
        destroy();
    }

}
