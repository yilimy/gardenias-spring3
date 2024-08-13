package com.example.rabbitmq.exchange.topic.consumer;

import com.example.rabbitmq.consumer.MessageConsumer;
import com.rabbitmq.client.Consumer;
import lombok.SneakyThrows;

/**
 * Dept类型的主题消费者
 * @author caimeng
 * @date 2024/8/13 16:15
 */
public class TopicDeptConsumer extends MessageConsumer {
    // 路由匹配
    protected static final String ROUTING_KEY_DEPT = "#.dept.#";

    public static void main(String[] args) {
        new TopicDeptConsumer().firstConsumer();
    }

    @Override
    @SneakyThrows
    public void firstConsumer() {
        initExchangeWithTopic();
        String groupQueueName = getGroupQueueName();
        channel.queueDeclare(groupQueueName, true, false, true, null);
        /*
         * 采用的是占位符匹配的主题模式
         */
        channel.queueBind(groupQueueName, EXCHANGE_TOPIC, ROUTING_KEY_DEPT);
        Consumer consumer = createAckConsumer();
        channel.basicConsume(groupQueueName, consumer);
    }

    /**
     * 消费方法
     */
    @Override
    protected void accept(String msg) {
        System.out.println("【部门接收消息】" + msg);
    }
}
