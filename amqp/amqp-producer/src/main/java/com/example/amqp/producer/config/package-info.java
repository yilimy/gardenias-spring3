/**
 * 生产端配置
 * <a href="https://www.bilibili.com/video/BV1jsYsezEbP/" />
 * <p>
 *     生产端是不需要考虑队列的，因此配置文件有所不同。
 * <p>
 *     发送消息的批处理
 *     {@link com.example.amqp.producer.config.RabbitMQConfig#batchQueueTaskScheduler()}
 *     {@link com.example.amqp.producer.config.RabbitMQConfig#amqpTemplateBatch(RetryTemplate, ConnectionFactory, TaskScheduler)}
 * @author caimeng
 * @date 2024/8/14 17:51
 */
package com.example.amqp.producer.config;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.scheduling.TaskScheduler;