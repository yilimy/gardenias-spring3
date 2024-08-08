package com.example.rabbitmq.producer;

import com.rabbitmq.client.MessageProperties;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

/**
 * 消息生产者
 * 指定为持久化的队列
 * @author caimeng
 * @date 2024/7/31 16:19
 */
@Slf4j
public class MessagePersistenceProducer extends MessageProducer {
    public static void main(String[] args) {
        new MessagePersistenceProducer().firstPublish();
    }

    /**
     * 执行完毕后，在 QueueAndStream 标签页中，Features D AD。
     * 其中 D表示持久化，Durable
     */
    @Override
    public void firstPublish() {
        // 使用持久化的队列
        initPersistence();
        produce();
        destroy();
    }

    @Override
    @SneakyThrows
    protected void produce() {
        long start = System.currentTimeMillis();
        CompletableFuture<Void>[] completableFutures = Stream.iterate(0, n -> n + 1)
                .limit(100)
                .map(n -> CompletableFuture.runAsync(() -> {
                    String msg = "【沐言科技 - " + n +"】www.yootk.com";
                    try {
                        /*
                         * 通过设置 props，指定发布时开启持久化
                         * 在消息发送的时候，追加了消息属性的持久化。
                         * 再次重启服务的时候，所有未消费的消息内容也可以自动的进行保存。
                         * 这样的配置才符合消费稳定性传输的需要。
                         */
                        channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, msg.getBytes(StandardCharsets.UTF_8));
                    } catch (IOException e) {
                        log.error("发布失败: " + msg, e);
                    }
                })).<CompletableFuture<Void>>toArray(CompletableFuture[]::new);
        CompletableFuture.allOf(completableFutures).join();
        long end = System.currentTimeMillis();
        System.out.println("【消息发送完毕】消息发送的耗时时间" + (end - start) + "ms");
    }
}
