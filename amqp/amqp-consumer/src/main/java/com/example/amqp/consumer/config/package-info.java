/**
 * 消费端配置
 * <a href="https://www.bilibili.com/video/BV1rT42167nZ/" />
 * 注解配置
 * <a href="https://www.bilibili.com/video/BV1LsYsezEWp/" />
 * <p>
 *     现在已经成功完成Spring与RabbitMQ的整合
 *     {@link com.example.amqp.consumer.config.RabbitMQConfig}
 *     但是现阶段的整合处理是通过纯粹的Bean方式完成的，尤其是在消费端进行配置的时候，还需要进行各种容器、绑定等处理，过于繁琐。
 *     将改为注解的方式配置。
 * <p>
 *     注解的方式
 *     {@link com.example.amqp.consumer.config.RabbitMQAnnotationConfig}
 *     {@link com.example.amqp.consumer.listener.AnnotationMessageListener}
 * <p>
 *     消息批处理
 *     {@link com.example.amqp.consumer.config.RabbitMQAnnotationConfig#rabbitListenerContainerBatchFactory(ConnectionFactory)}
 *     {@link com.example.amqp.consumer.listener.AnnotationObjectBatchListener}
 * @author caimeng
 * @date 2024/8/14 17:44
 */
package com.example.amqp.consumer.config;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;