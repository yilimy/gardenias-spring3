/**
 * 在之前进行配置的时候，多个消费端绑定在一个队列上，形成工作队列的形式。
 * 使用exchange发布消息的生产者
 *    1. 生产者声明了exchange
 *          exchangeDeclare
 *    2. 生产者向exchange中发布了消息
 *          basicPublish
 *      {@link com.example.rabbitmq.exchange.fanout.MessageFanoutProducer}
 * 消费者分组:
 *    1. 消费者声明exchange
 *          exchangeDeclare
 *    2. 消费者声明了队列 (因为生产者没有声明，故要在消费端声明)
 *          queueDeclare
 *    3. 消费者绑定了exchange和队列
 *          queueBind
 *    4. 消费者注册消费
 *          basicConsume
 *      {@link com.example.rabbitmq.exchange.fanout.GroupAFanoutConsumerA}
 *      {@link com.example.rabbitmq.exchange.fanout.GroupAFanoutConsumerB}
 *      {@link com.example.rabbitmq.exchange.fanout.GroupBFanoutConsumer}
 *      {@link com.example.rabbitmq.exchange.fanout.GroupCFanoutConsumer}
 * 测试：
 *      1. 先启动所有的四个消费端
 *      2. 使用生产端发送消息
 * 结果：
 *      1. 同组下，是竞争关系
 *      2. 同exchange但不同组下，是平行关系
 *      fanout是针对队列的广播，每个队列上有多个消费者，每个消费者会消费不同的数据。
 * @author caimeng
 * @date 2024/8/8 16:37
 */
package com.example.rabbitmq.exchange.fanout;