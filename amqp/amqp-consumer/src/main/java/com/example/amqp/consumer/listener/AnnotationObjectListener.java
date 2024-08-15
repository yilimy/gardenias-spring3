package com.example.amqp.consumer.listener;

import com.example.amqp.common.Dept;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * 监听rabbitmq的消息
 * 相对于 {@link AnnotationMessageListener},接收的消息不是Message对象，而是业务实体对象
 * @author caimeng
 * @date 2024/8/14 10:19
 */
@Slf4j
@Component
@ConditionalOnProperty(value = "handler.type",havingValue = "obj", matchIfMissing = true)
public class AnnotationObjectListener {
    /**
     * 接收消息的内容
     * @param dept 消息实体对象
     */
    // 使用新注解的时候，一定要注意是否开启了该功能，比如 RabbitListener 需要注解 @EnableRabbit 开启功能
    @RabbitListener(
            queues = "muyan.consumer.queue",
            // admin配置不能少，该配置绑定了exchange和消息队列
            admin = "admin",
            // 在配置中注入的名字
            containerFactory = "rabbitListenerContainerFactory"
    )
    public void handler(Dept dept) {
        log.info("【接收消息】部门信息: {}", dept);
    }
}
