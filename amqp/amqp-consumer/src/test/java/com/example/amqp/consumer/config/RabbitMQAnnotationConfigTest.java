package com.example.amqp.consumer.config;

import com.example.amqp.consumer.StartAMQPConsumer;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.concurrent.TimeUnit;

/**
 * 测试rabbitmq的配置是否成功
 * <p>
 *     使用注解的方式
 * @author caimeng
 * @date 2024/8/14 17:19
 */
@ContextConfiguration(classes = StartAMQPConsumer.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {
        "spring.profiles.active=dev",
//        "handler.type=string",
        "handler.type=batch",
        "rabbit.batch.enable=true",
})
public class RabbitMQAnnotationConfigTest {

    /**
     * 监听测试
     */
    @SneakyThrows
    @Test
    public void receiveTest() {
        TimeUnit.SECONDS.sleep(Long.MAX_VALUE);
    }
}
