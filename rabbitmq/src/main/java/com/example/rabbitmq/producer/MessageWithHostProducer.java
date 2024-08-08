package com.example.rabbitmq.producer;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * 消息生产者
 * 配置了虚拟主机的生产者
 * @author caimeng
 * @date 2024/7/31 16:19
 */
@Slf4j
public class MessageWithHostProducer extends MessageProducer {

    public static void main(String[] args) {
        new MessageWithHostProducer().firstPublish();
    }

    @Override
    @SneakyThrows
    public void firstPublish() {
        initWithHost();
        produce();
        destroy();
    }
}
