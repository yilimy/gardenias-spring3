package com.example.amqp.consumer.listener;

import com.example.amqp.common.Dept;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 监听rabbitmq的消息
 * 相对于 {@link AnnotationObjectListener},使用的是批处理
 * @author caimeng
 * @date 2024/8/14 10:19
 */
@Slf4j
@Component
@ConditionalOnProperty(value = "handler.type",havingValue = "batch", matchIfMissing = true)
public class AnnotationObjectBatchListener {
    /**
     * 接收消息的内容
     * <p>
     *     批处理，参数改为 list 接收
     * @param deptList 消息实体对象
     */
    @RabbitListener(
            queues = "muyan.consumer.queue",
            admin = "admin",
            // 需要改成批处理的容器监听配置
            containerFactory = "rabbitListenerContainerBatchFactory"
    )
    public void handler(List<Dept> deptList) {
        // org.springframework.amqp.rabbit.RabbitListenerEndpointContainer#0-5
        String threadName = Thread.currentThread().getName();
        deptList.forEach(item -> System.out.println(threadName + " 【接收消息】部门信息: " + item));
    }
}
