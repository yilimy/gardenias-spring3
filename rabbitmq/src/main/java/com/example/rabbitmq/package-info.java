/**
 * 消息组件 rabbitmq
 * <p>
 *     创建RabbitMQ消息生产者
 *     <a href="https://www.bilibili.com/video/BV1RW421X7vB/" />
 * <p>
 *     如果想要获取RabbitMQ的连接信息，则需要通过ConnectionFactory处理类来进行配置，
 *     在这个类中需要配置所有的连接信息，这个工厂类中提供了new Connection()的方法
 * <p>
 *     消息消费应答
 *     <a href="https://www.bilibili.com/video/BV1kZ421N7k4/" />
 *     如果消息没有被消费端正确的消费？
 *          - 丢失
 *          - 延迟
 *          - 错误
 *          - 重复
 *     导致消费端没有收到消息，RabbitMQ里提供了应答机制：
 *          只有消费端发送了应答数据之后，RabbitMQ才会执行消息的删除，
 *          否则消息的重投处理。
 *     如果某些消费端一直没有进行ACK应答信息回复的时候，那么RabbitMQ会重复地将已经消费的消息进行重新发送，
 *          （将消息放回到发送队列中，进行重新投递）
 *          从而可能造成内存泄漏，导致RabbitMQ服务宕机，所有需要进行ACK应答处理。
 *          - 自动应答机制，通过channel提供的方法即可
 *                  {@link com.example.rabbitmq.consumer.MessageConsumer#firstConsumerAutoACK()}
 *                  {@link com.rabbitmq.client.Channel#basicConsume(String, boolean, Consumer)}
 *          - 手工应答处理，主要是在Consumer类中完成
 *                  {@link com.example.rabbitmq.consumer.MessageConsumer#firstConsumerManualACK()}
 * @author caimeng
 * @date 2024/7/31 16:16
 */
package com.example.rabbitmq;

import com.rabbitmq.client.Consumer;