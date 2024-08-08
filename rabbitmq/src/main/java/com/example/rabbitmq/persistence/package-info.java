/**
 * 消息的持久化
 * <a href="https://www.bilibili.com/video/BV1rE4m1R72u/" />
 * <p>
 *     队列是保存消息的核心单元，如果这些消息已经发送到队列中，并且没有被消费掉，那么这些数据应该被保存下来，
 *     但是对于当前所使用的消息队列，只是一个普通的瞬时队列（重启后就没了）。
 * <p>
 *     控制台中的单词解释：
 *     AD - Overview中的FFeatures中 Auto Delete
 *     Message中的Ready - 未被消费的消息数量
 *     Durability - 队列持久化配置
 *          Transient - 瞬时队列
 * <p>
 *     重启RabbitMQ (Linux环境)，windows手动关进程
 *          /usr/local/rabbitmq/sbin/rabbitmq-server start > /dev/null 2>&1 &
 *          /usr/local/rabbitmq/sbin/rabbitmq-plugins enable rabbitmq_management
 * <p>
 *     瞬时队列是不可能在实际的工作中使用的，数据不能丢失的，工作中应该采用的是持久化的队列。
 *     如果要创建持久化的队列（Durable）可以在Channel接口中配置
 *     {@link com.rabbitmq.client.Channel#queueDeclare(String, boolean, boolean, boolean, Map)}
 *     第二个参数，是持久化队列的参数
 * @author caimeng
 * @date 2024/8/5 18:36
 */
package com.example.rabbitmq.persistence;

import java.util.Map;