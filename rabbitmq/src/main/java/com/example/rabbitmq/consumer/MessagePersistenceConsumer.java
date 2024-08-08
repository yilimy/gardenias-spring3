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
public class MessagePersistenceConsumer extends MessageConsumer {

    public static void main(String[] args) {
        new MessagePersistenceConsumer().firstConsumer();
    }

    @Override
    @SneakyThrows
    public void firstConsumer() {
        // 指定为持久化的消息队列
        initPersistence();
        Consumer consumer = createConsumer();
        channel.basicConsume(QUEUE_NAME, consumer);
    }
}
