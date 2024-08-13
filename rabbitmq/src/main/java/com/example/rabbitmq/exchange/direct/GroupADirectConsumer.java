package com.example.rabbitmq.exchange.direct;

import com.example.rabbitmq.RabbitMQServiceAbs;
import com.example.rabbitmq.consumer.MessageConsumer;
import com.rabbitmq.client.Consumer;
import lombok.SneakyThrows;

/**
 * 创建一个消费者，该消费者与生产者拥有一致的exchange,但是routingKey不一致。
 * @author caimeng
 * @date 2024/8/13 14:22
 */
public class GroupADirectConsumer extends MessageConsumer {
    protected static final String ROUTING_KEY_CONSUMER_A = "muyan.routing.key";

    public static void main(String[] args) {
        new GroupADirectConsumer().firstConsumer();
    }

    /**
     * 注意此处绑定的routingKey {@link GroupADirectConsumer#ROUTING_KEY_CONSUMER_A}
     * 与生产者设定的routingKey {@link RabbitMQServiceAbs#ROUTING_KEY} 不一致
     */
    @Override
    @SneakyThrows
    public void firstConsumer() {
        initExchangeWithDirect();
        String groupQueueName = getGroupQueueName();
        channel.queueDeclare(groupQueueName, true, false, true, null);
        /*
         * exchange 要与生产者一致
         * 注意此处绑定的routingKey (ROUTING_KEY_CONSUMER_A) 与生产者设定的routingKey (ROUTING_KEY) 不一致
         */
        channel.queueBind(groupQueueName, EXCHANGE_DIRECT, ROUTING_KEY_CONSUMER_A);
        Consumer consumer = createAckConsumer();
        channel.basicConsume(groupQueueName, consumer);
    }

    /**
     * 消费方法
     */
    @Override
    protected void accept(String msg) {
        System.out.println("【消费组A - direct - 接收消息】" + msg);
    }
}
