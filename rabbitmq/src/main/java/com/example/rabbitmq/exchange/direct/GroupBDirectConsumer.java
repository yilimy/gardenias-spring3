package com.example.rabbitmq.exchange.direct;

import com.example.rabbitmq.consumer.MessageConsumer;
import com.rabbitmq.client.Consumer;
import lombok.SneakyThrows;

/**
 * 创建一个消费者，该消费者与生产者拥有一致的 exchange 和 routingKey
 * @author caimeng
 * @date 2024/8/13 14:22
 */
public class GroupBDirectConsumer extends MessageConsumer {

    public static void main(String[] args) {
        new GroupBDirectConsumer().firstConsumer();
    }
    @Override
    @SneakyThrows
    public void firstConsumer() {
        initExchangeWithDirect();
        String groupQueueName = getGroupQueueName();
        channel.queueDeclare(groupQueueName, true, false, true, null);
        /*
         * exchange 和 routingKey 与生产者一致
         */
        channel.queueBind(groupQueueName, EXCHANGE_DIRECT, ROUTING_KEY);
        Consumer consumer = createAckConsumer();
        channel.basicConsume(groupQueueName, consumer);
    }

    /**
     * 消费方法
     */
    @Override
    protected void accept(String msg) {
        System.out.println("【消费组B - direct - 接收消息】" + msg);
    }

    /**
     * 重新定义组名
     * @return 组名
     */
    @Override
    protected String getGroupQueueName() {
        return "yootk.b.group.queue";
    }
}
