/**
 * 主题模式
 * <a href="https://www.bilibili.com/video/BV1nn4y1f7Tv/" />
 * 主题模式与直连模式相比，最大的特点是在于可以基于表达式的匹配操作来实行routingKey的处理。
 * 在直连模式下，routingKey采用的是全匹配的方式；
 * 而在topic模式下，可以基于两个占位符进行自动匹配，
 *      "*" 匹配0个或1个单词
 *      "#" 匹配0个、1个或多个单词
 * <p>
 *     两个不同主题的消费者
 *     {@link com.example.rabbitmq.exchange.topic.producer.TopicERPProducer}
 *     {@link com.example.rabbitmq.exchange.topic.producer.TopicCRMProducer}
 *     两个不同主题的消费者
 *     {@link com.example.rabbitmq.exchange.topic.consumer.TopicDeptConsumer}
 *     {@link com.example.rabbitmq.exchange.topic.consumer.TopicEmpConsumer}
 * <p>
 *     主题过滤是为了将消息分发给不同的队列。
 *     {@link com.example.rabbitmq.exchange.topic.consumer.TopicEmpConsumer#getGroupQueueName()}
 * @author caimeng
 * @date 2024/8/13 15:20
 */
package com.example.rabbitmq.exchange.topic;