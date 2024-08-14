package com.example.amqp.consumer.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

/**
 * 监听rabbitmq的消息
 * @author caimeng
 * @date 2024/8/14 10:19
 */
@Slf4j
public class RabbitMQMessageListener implements MessageListener {
    /**
     * 接收消息的内容
     * @param message 消息
     */
    @Override
    public void onMessage(Message message) {
        log.info("【接收消息】消息内容: {}", new String(message.getBody()));
    }
}
