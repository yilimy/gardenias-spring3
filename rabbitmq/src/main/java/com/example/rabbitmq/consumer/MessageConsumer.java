package com.example.rabbitmq.consumer;

import com.example.rabbitmq.RabbitMQServiceAbs;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import lombok.SneakyThrows;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * 消费者
 * <p>
 *     正常情况下，只要是消费端连上了服务端，那么所有的连接会持续进行状态的维护，只要有信息发送过来，那么就立即进行消费处理。
 * @author caimeng
 * @date 2024/8/1 13:48
 */
public class MessageConsumer extends RabbitMQServiceAbs {
    private static int count = 0;

    public static void main(String[] args) {
        new MessageConsumer().firstConsumer();
    }

    @SneakyThrows
    public void firstConsumer() {
        init();
        Consumer consumer = createConsumer();
        // 连接队列与消费者
        channel.basicConsume(QUEUE_NAME, consumer);
    }

    protected Consumer createConsumer() {
        return new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) {
                // 在实际的开发中，在此方法内部需要进行业务接口的方法调用
                try {
                    // 每隔1秒消费一次
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println("【" + count++ +"】消息的消费处理");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 获取消息内容
                String msg = new String(body, StandardCharsets.UTF_8);
                System.out.println("【接收消息】" + msg);
            }
        };
    }

    /**
     * 自动应答ACK
     */
    @SneakyThrows
    public void firstConsumerAutoACK() {
        init();
        Consumer consumer = createConsumer();
        // 连接队列与消费者
        channel.basicConsume(QUEUE_NAME, true, consumer);
    }


    /**
     * 使用手工应答
     */
    @SneakyThrows
    public void firstConsumerManualACK() {
        init();
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                // 在实际的开发中，在此方法内部需要进行业务接口的方法调用
                try {
                    // 每隔1秒消费一次
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println("【" + count++ +"】消息的消费处理");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 获取消息内容
                String msg = new String(body, StandardCharsets.UTF_8);
                System.out.println("【接收消息】" + msg);
                /*
                 * 手工应答ACK
                 * 参数 multiple 如果设置为false表示只对当前消息做应答
                 * 如果设置为true，则表示对所有消息进行应答
                 */
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        // 连接队列与消费者
        channel.basicConsume(QUEUE_NAME, consumer);
    }
}
