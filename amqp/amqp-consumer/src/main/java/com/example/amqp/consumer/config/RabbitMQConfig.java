package com.example.amqp.consumer.config;

import com.example.amqp.consumer.listener.RabbitMQMessageListener;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.PooledChannelConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.support.RetryTemplate;

/**
 * RabbitMQ的配置类
 * @author caimeng
 * @date 2024/8/14 10:25
 */
@Configuration
@PropertySource("classpath:config/amqp-${spring.profiles.active}.properties")
// 使用bean的方式配置，对应：使用注解的方式实现
@ConditionalOnProperty(value = "config.type", havingValue = "bean")
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
    @Value("${amqp.rabbit.routing.key:}")
    private String routingKey;
    @Value("${amqp.rabbit.exchange.name:}")
    private String exchangeName;
    @Value("${amqp.rabbit.queue.name:}")
    private String queueName;

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
     * 队列的声明
     * @return 队列
     */
    @Bean
    public Queue queue() {
        return new Queue(this.queueName, true, false, true);
    }

    /**
     * @return 自定义的消息监听器
     */
    @Bean
    public RabbitMQMessageListener rabbitMQMessageListener() {
        return new RabbitMQMessageListener();
    }

    /**
     * @return 监听容器
     */
    @Bean
    public SimpleMessageListenerContainer simpleMessageListenerContainer(
            RabbitMQMessageListener rabbitMQMessageListener,
            Queue queue, ConnectionFactory factory) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(factory);
        // 并行的消费端数量
        container.setConcurrentConsumers(5);
        // 最大的并行消费端数量
        container.setMaxConcurrentConsumers(10);
        // 设置消息监听器
        container.setMessageListener(rabbitMQMessageListener);
        // 自动应答
        container.setAcknowledgeMode(AcknowledgeMode.AUTO);
        // 追加队列
        container.addQueues(queue);
        // 容器初始化
        container.initialize();
        return container;
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
     * 绑定交换器和队列
     * @param exchange 交换器
     * @param queue 队列
     * @return 绑定对象
     */
    @Bean
    public Binding binding(Exchange exchange, Queue queue) {
        // 将队列绑定到交换器中，并指定routingKey
        return BindingBuilder.bind(queue).to(exchange).with(this.routingKey).noargs();
    }

    /**
     * 整合处理的对象
     * <p>
     *     最关键的配置项，也是与生产端的不同之处。
     * @param retryTemplate 重试模板
     * @param binding 绑定关系
     * @param exchange 交换器
     * @param queue 消息队列
     * @param factory 连接工厂
     * @return 整合结果
     */
    @Bean
    @Primary
    public RabbitAdmin admin(RetryTemplate retryTemplate, Binding binding, Exchange exchange,
                             Queue queue, ConnectionFactory factory) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(factory);
        // 设置重试模板
        rabbitAdmin.setRetryTemplate(retryTemplate);
        // 声明交换空间 or 交换器
        rabbitAdmin.declareExchange(exchange);
        // 声明消息队列
        rabbitAdmin.declareQueue(queue);
        // 声明交换器和消息队列的绑定关系
        rabbitAdmin.declareBinding(binding);
        return rabbitAdmin;
    }
}
