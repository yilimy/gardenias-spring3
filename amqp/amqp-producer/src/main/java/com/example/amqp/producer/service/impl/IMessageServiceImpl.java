package com.example.amqp.producer.service.impl;

import com.example.amqp.producer.service.IMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

/**
 * @author caimeng
 * @date 2024/8/14 18:12
 */
@Slf4j
@Service
// 配置资源文件
@PropertySource("classpath:config/amqp.properties")
public class IMessageServiceImpl implements IMessageService {
    @Value("${amqp.rabbit.routing.key:}")
    private String routingKey;
    @Autowired
    private AmqpTemplate amqpTemplate;
    @Override
    public void send(String msg) {
        amqpTemplate.convertAndSend(routingKey, msg);
    }
}
