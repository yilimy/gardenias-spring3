/**
 * 消息组件 rabbitmq
 * 页面 <a href="http://localhost:15672/" />
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
 * <p>
 *     RabbitMQ虚拟主机
 *     <a href="https://www.bilibili.com/video/BV1G4421S7XS/" />
 *     消息组件是项目必备的组件之一，但不是每个项目都要单独配置消息组件。
 *     常见的做法是做一个专属的消息服务集群（或单机），该服务上可能要同时拥有不同的操作空间。
 *     为了方便业务划分，RabbitMQ支持虚拟主机的配置。
 *     虚拟主机中，不同的用户具有不同的权限，比如：只能生产或者只能消费。
 *          - RocketMQ是在新版本中才添加权限控制，采用的是基于业务识别码的方式。
 *     考虑到实用性，一般使用命令行的方式进行配置。
 *          - RabbitMQ所有的图形化界面操作，都能通过命令行的方式实现；
 *          - 创建一个虚拟主机 : rabbitmqctl add_vhost YootkVHost, 这种方式创建虚拟主机没有设置用户
 *          - 查询VHost : rabbitmqctl list_vhosts
 *          - VHost权限 : rabbitmqctl set_permissions -p YootkVHost yootk .* .* .* 表示给用户yootk配置了conf | write | read 三项所有的权限
 *          - 删除VHost : rabbitmqctl delete_vhost YootkVHost
 * <p>
 *     fanout广播模式
 *     <a href="https://www.bilibili.com/video/BV1e4421Z7dP/" />
 *     利用Exchange可以实现不同的消息处理模式。
 *     JMS是java定义的消息标准，JMS的消息模式：
 *          - 点对点
 *          - 主题消息，一对多消息
 *     {@link com.example.rabbitmq.consumer.MessageConsumerA}
 *     {@link com.example.rabbitmq.consumer.MessageConsumerB}
 *     多个消费者不会重复进行消息处理，现在的所有的消息处理，都只能是默认形态下处理，还没有涉及Exchange的话题。
 * <p>
 *     除了基本的消息队列之外，在RabbitMQ中还提供了Exchange交换机、RoutingKey路由Key等概念，
 *     同时在Channel接口中提供的 basicPublish() 方法中也提供有对应的参数支持。
 *     三种模式：
 *      1. 基于Exchange的广播模式 fanout
 *      2. 直连模式 direct
 *      3. 主题模式 topic
 * @author caimeng
 * @date 2024/7/31 16:16
 */
package com.example.rabbitmq;

import com.rabbitmq.client.Consumer;