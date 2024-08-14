package com.example.amqp.producer.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.PooledChannelConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.support.RetryTemplate;

/**
 * RabbitMQ生产端的配置类
 * <p>
 *     使用的是exchange，因此生产端没有队列的设置
 * <p>
 *     注意：在该配置类中并没有配置routingKey，而是选择在service层配置
 *     {@link com.example.amqp.producer.service.impl.IMessageServiceImpl#routingKey}
 * @author caimeng
 * @date 2024/8/14 10:25
 */
@Configuration
@PropertySource("classpath:config/amqp.properties")
public class RabbitMQConfig {
    @Value("${amqp.rabbit.host:localhost}")
    private String host;
    @Value("${amqp.rabbit.port:5672}")
    private Integer port;
    @Value("${amqp.rabbit.username:}")
    private String username;
    @Value("${amqp.rabbit.password:}")
    private String password;
    @Value("${amqp.rabbit.vhost:}")
    private String vhost;
    @Value("${amqp.rabbit.exchange.name:}")
    private String exchangeName;

    /**
     * spring管理的rabbitmq的连接工厂
     * @return spring的连接工厂
     */
    @Bean
    public ConnectionFactory springConnectionFactory() {
        // rabbitmq的连接工厂
        com.rabbitmq.client.ConnectionFactory factory = new com.rabbitmq.client.ConnectionFactory();
        factory.setHost(this.host);
        factory.setPort(this.port);
        factory.setUsername(this.username);
        factory.setPassword(this.password);
        factory.setVirtualHost(this.vhost);
        // 转化为spring的连接工厂
        return new PooledChannelConnectionFactory(factory);
    }

    /**
     * @return 重试Bean
     */
    @Bean
    public RetryTemplate retryTemplate() {
        // 重试的模板类
        RetryTemplate retryTemplate = new RetryTemplate();
        // 设置失败的策略
        ExponentialBackOffPolicy policy = new ExponentialBackOffPolicy();
        // 重试的间隔时间，单位：毫秒
        policy.setInitialInterval(500);
        // 重试的最大间隔
        policy.setMaxInterval(10000);
        // 重试的倍数，每次涨10倍: 500, 5000, 50000
        policy.setMultiplier(10.0);
        // 设置重试策略
        retryTemplate.setBackOffPolicy(policy);
        return retryTemplate;
    }

    /**
     * 配置交换空间
     * <p>
     *     此处使用的是fanout {@link FanoutExchange}
     *     其他两种消息模式：
     *          直连消息 {@link org.springframework.amqp.core.DirectExchange}
     *          主题消息 {@link org.springframework.amqp.core.TopicExchange}
     * @return 交换器
     */
    @Bean
    public Exchange exchange() {
        return new FanoutExchange(this.exchangeName);
    }

    /**
     * 生产端模板
     * <p>
     *     最终消息的发送处理，是由专属的模板类提供支持的。
     * <p>
     *     与消费端的不同之处
     * @return 生产端模板
     */
    @Bean
    public AmqpTemplate amqpTemplate(RetryTemplate retryTemplate, ConnectionFactory factory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(factory);
        rabbitTemplate.setExchange(this.exchangeName);
        rabbitTemplate.setRetryTemplate(retryTemplate);
        return rabbitTemplate;
    }
}
