package com.example.rabbitmq.consumer;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import lombok.SneakyThrows;

import java.nio.charset.StandardCharsets;

/**
 * 消费者
 * <p>
 *     正常情况下，只要是消费端连上了服务端，那么所有的连接会持续进行状态的维护，只要有信息发送过来，那么就立即进行消费处理。
 * @author caimeng
 * @date 2024/8/1 13:48
 */
public class MessageConsumerB extends MessageConsumer {

    public static void main(String[] args) {
        new MessageConsumerA().firstConsumer();
    }

    @Override
    protected Consumer createConsumer() {
        return new DefaultConsumer(channel) {
            @SneakyThrows
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) {
                // 获取消息内容
                String msg = new String(body, StandardCharsets.UTF_8);
                System.out.println("【消费B - 接收消息】" + msg);
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
    }
}
