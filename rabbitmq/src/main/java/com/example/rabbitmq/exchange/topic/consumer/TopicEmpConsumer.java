package com.example.rabbitmq.exchange.topic.consumer;

import com.example.rabbitmq.consumer.MessageConsumer;
import com.rabbitmq.client.Consumer;
import lombok.SneakyThrows;

/**
 * Dept类型的主题消费者
 * @author caimeng
 * @date 2024/8/13 16:15
 */
public class TopicEmpConsumer extends MessageConsumer {
    // 路由匹配
    protected static final String ROUTING_KEY_EMP = "#.emp.#";

    public static void main(String[] args) {
        new TopicEmpConsumer().firstConsumer();
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
        channel.queueBind(groupQueueName, EXCHANGE_TOPIC, ROUTING_KEY_EMP);
        Consumer consumer = createAckConsumer();
        channel.basicConsume(groupQueueName, consumer);
    }

    /**
     * 消费方法
     */
    @Override
    protected void accept(String msg) {
        System.out.println("【雇员接收消息】" + msg);
    }

    /**
     * 必须与 {@link TopicDeptConsumer} 处于不同的队列，否则主题匹配不生效，处于统一队列中的消费者处于竞争关系。
     * 也就是说，主题匹配是为了把消息发送给不同的队列
     * @return 队列名
     */
    @Override
    protected String getGroupQueueName() {
        return "yootk.b.group.queue";
    }
}
