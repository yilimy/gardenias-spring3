package com.example.amqp.consumer.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 监听rabbitmq的消息
 * <p>
 *     通过注解的方式实现
 *     1. 通过 Component 扫描注册
 *     2. 取消强制的接口实现  (强制的接口实现一定不是好设计)
 *          {@link MessageListener}
 *          {@link RabbitMQMessageListener}
 *     3. 添加注解 RabbitListener
 * @author caimeng
 * @date 2024/8/14 10:19
 */
@Slf4j
@Component
public class AnnotationMessageListener {
    /**
     * 接收消息的内容
     * @param message 消息
     */
    // 使用新注解的时候，一定要注意是否开启了该功能，比如 RabbitListener 需要注解 @EnableRabbit 开启功能
    @RabbitListener(
            queues = "muyan.consumer.queue",
            // admin配置不能少，该配置绑定了exchange和消息队列
            admin = "admin",
            // 在配置中注入的名字
            containerFactory = "rabbitListenerContainerFactory"
    )
    public void handler(Message message) {
        log.info("【接收消息】消息内容: {}", new String(message.getBody()));
    }
}
