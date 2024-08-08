package com.example.rabbitmq.consumer;

import com.rabbitmq.client.Consumer;
import lombok.SneakyThrows;

/**
 * 消费者
 * <p>
 *     持久化的消息队列
 * @author caimeng
 * @date 2024/8/1 13:48
 */
public class MessageWithHostConsumer extends MessageConsumer {

    public static void main(String[] args) {
        new MessageWithHostConsumer().firstConsumer();
    }

    @Override
    @SneakyThrows
    public void firstConsumer() {
        // 指定了虚拟主机的初始化
        initWithHost();
        Consumer consumer = createConsumer();
        channel.basicConsume(QUEUE_NAME, consumer);
    }
}
