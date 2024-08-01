package com.example.rabbitmq.consumer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * @author caimeng
 * @date 2024/8/1 14:12
 */
@ExtendWith(MockitoExtension.class)
public class MessageConsumerTest {
    @Spy
    private MessageConsumer messageConsumer;

    /**
     * @deprecated 测不出消息接收，改为在类的main方法中测试 {@link MessageConsumer#main(String[])}
     */
    @Deprecated
    @Test
    public void firstConsumerTest() {
        messageConsumer.firstConsumer();
    }
}
