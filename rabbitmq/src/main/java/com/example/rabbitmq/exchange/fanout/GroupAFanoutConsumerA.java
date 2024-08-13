package com.example.rabbitmq.exchange.fanout;

import com.example.rabbitmq.consumer.MessageConsumer;
import com.rabbitmq.client.Consumer;
import lombok.SneakyThrows;

/**
 * 消费者
 * <p>
 *     分组A中的消费者A
 * <p>
 *     要进行exchange的消费，需要使用同生产者一样的exchange
 *     {@link MessageFanoutProducer#firstPublish()}
 * @author caimeng
 * @date 2024/8/1 13:48
 */
public class GroupAFanoutConsumerA extends MessageConsumer {

    public static void main(String[] args) {
        new GroupAFanoutConsumerA().firstConsumer();
    }

    @Override
    @SneakyThrows
    public void firstConsumer() {
        initWithHostAndExchange();
        String groupQueueName = getGroupQueueName();
        // exchange模式中，生产者不创建队列，由消费者创建队列去消费
        channel.queueDeclare(groupQueueName, true, false, true, null);
        /*
         * 队列需要与Exchange进行绑定
         * 路由的key (routingKey) 先为空串，该配置影响着fanout和direct。
         */
        channel.queueBind(groupQueueName, EXCHANGE_NAME, "");
        Consumer consumer = createAckConsumer();
        channel.basicConsume(groupQueueName, consumer);
    }

    /**
     * 消费方法
     */
    @Override
    protected void accept(String msg) {
        System.out.println("【消费组A - fanout-A - 接收消息】" + msg);
    }
}
