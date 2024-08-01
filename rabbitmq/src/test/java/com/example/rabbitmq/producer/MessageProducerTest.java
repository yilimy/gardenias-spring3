package com.example.rabbitmq.producer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * @author caimeng
 * @date 2024/8/1 9:57
 */
@ExtendWith(MockitoExtension.class)
public class MessageProducerTest {
    @Spy
    private MessageProducer messageProducer;
    @Test
    public void firstPublishTest() {
        messageProducer.firstPublish();
    }
}
