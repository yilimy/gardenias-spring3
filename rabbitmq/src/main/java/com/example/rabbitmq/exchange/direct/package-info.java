/**
 * 直连模式
 * <a href="https://www.bilibili.com/video/BV1Zw4m1r7ii/" />
 * <p>
 *     routingKey 与直连模式
 *      使用fanout使得同一个交换区之中的不同队列，可以接收到相同的消息，但是在fanout中有个routingKey是在方法中定义的。
 *      {@link com.rabbitmq.client.Channel#queueBind(String, String, String)}
 *      之前在fanout中配置的routingKey都是空串，相当于所有队列中的routingKey是统一的，表示实现了广播消息的发送处理。
 *      如果要设置routingKey的时候，就说明要实现直连模式。
 * <p>
 *     生产者 {@link com.example.rabbitmq.exchange.direct.DirectProducer}
 *     消费者 {@link com.example.rabbitmq.exchange.direct.GroupADirectConsumer}
 *          该消费者与生产有相同的exchange，但是routingKey不一致
 *     消费者 {@link com.example.rabbitmq.exchange.direct.GroupBDirectConsumer}
 *          该消费者与生产有相同的 exchange 和 routingKey
 *     消费者 {@link com.example.rabbitmq.exchange.direct.GroupCDirectConsumer}
 *          该消费者与生产有相同的 exchange 和 routingKey, 与消费者B不同消费组
 *     结果：
 *          GroupADirectConsumer 没有收到消息
 *          GroupBDirectConsumer 消费了所有的消息
 *          GroupCDirectConsumer 消费了所有的消息
 *          消费者与生产者拥有相同的routingKey，可以进行正常的消费处理。
 *          如果有其他消费者工作在不同的队列，但是会匹配到同一个 exchange 和 routingKey，就会变成局部广播模式。
 * @author caimeng
 * @date 2024/8/13 11:35
 */
package com.example.rabbitmq.exchange.direct;