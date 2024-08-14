package com.example.amqp.producer.service;

import com.example.amqp.producer.StartAMQPProducer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * 测试消息发送
 * @author caimeng
 * @date 2024/8/14 18:20
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = StartAMQPProducer.class)
public class IMessageServiceTest {
    @Autowired
    private IMessageService iMessageService;

    /**
     * 测试消息发送
     * <p>
     *     为了方便测试消息发送的内容，建议先启动消费端的监听
     */
    @Test
    public void sendTest() {
        // 消息的发送
        iMessageService.send("沐言科技:www.yootk.com");
    }
}
