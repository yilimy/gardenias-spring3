/**
 * AMQP协议简介（Advanced Message Queuing Protocol 高级消息队列协议）
 * <a href="https://www.bilibili.com/video/BV1Yb421n7vV/" />
 * <p>
 *     RabbitMQ消息组件，是现在最稳定的消息组件，主要用于金融行业。遵循的是AMQP协议。
 *     在所有的异构系统 | 高并发系统，一般都会使用消息组件来实现高峰流量的处理。
 *     传统的程序开发之中，追求的是快速响应处理。
 *          用户只要发起了请求，那么就要立刻对该请求的业务进行处理。
 *          这样的设计会导致在高并发状态下的请求会拥堵。
 *     如果引入了消息组件，相当于提供了一个操作的缓冲区。
 *         队列是一种FIFO的设计结构，根据队列实现了分布式的队列开发，这就是消息组件的核心设计思想。
 * <p>
 *     在早期的javaEE设计开发之中，提供有一个JMS（java消息服务）标准，并且这个标准属于javaEE开发之中最基本的设计要求，
 *     这个协议|服务仅仅是对数据结构上的优化。
 *          使用过很多消息组件之后，会发现在整个系统里面，只有JMS支持的消息类型是最多的，
 *          但是它并不是一套标准的协议，所以仅仅能够在java行业中去使用。
 *     现阶段可以看见唯一的JMS还能够运行的组件就是由Apache维护的ActiveMQ。
 *     <a href="https://activemq.apache.org/" />
 *     (过去式)
 * <p>
 *     为了解决消息组件的传输性能，以及消息服务的标准化问题，所以提出了 AMQP 协议，
 *     只要基于此协议开发的消息组件，可以直接在任何的编程语言上实现。
 *     只是现阶段可以见到的AMQP协议的组件，只有RabbitMQ。
 * <p>
 *     所有的消息组件的核心之中，就是生产者和消费者。
 *     生产者负责生产数据，一般就是业务的调用者，
 *     消费者就是业务的终端，每一次获取到生产者发送来的数据，通过这些数据实现最终业务层的逻辑处理。
 * <p>
 *     协议组成：
 *          模型层：交互器 | 消息队列 | 访问控制
 *          会话层：操作命令 | 消息确认 | 数据同步
 *          传输层：多路复用 | 数据编码 | 心跳数据 | 异常处理 | 数据分帧
 * <p>
 *     不同的消息组件仅仅是在中间消息处理的地方有不同的逻辑，以RabbitMQ介绍核心组件：
 *          - Exchange 交换机 ： 实现消息生产者的消息接收，按照一定的规则，将消息发送到指定的消息队列中；
 *          - Queue    消息队列： 实现消息的存储，直到消息被安全的投递给消息消费者；
 *          - Bindings 绑定 ：   定义Exchange和Queue之间的关系，并提供消息路由规则的配置。
 * <p>
 *     rabbitMQ安装
 *     1. Erlang : <a href="https://www.erlang.org/downloads" />
 *     2. RabbitMQ : <a href="https://github.com/rabbitmq/rabbitmq-server/releases/tag/v3.13.6" />
 *     3. 启动：
 *          .\rabbitmq-plugins.bat enable rabbitmq_management
 *          重启windows的service中的RabbitMQ(自启动)，使上面的配置生效。
 *          rabbitmq-plugins.bat位于 D:\Program Files\RabbitMQ Server\rabbitmq_server-3.13.6\sbin
 *          本地访问 <a href="http://localhost:15672/" />   guest / guest
 * <p>
 *     配置wxWidgets组件库
 *     <a href="https://www.bilibili.com/video/BV1jS411w7cZ/" />
 *     对于现在的 RabbitMQ 来讲不要轻易的选择最新的版本，一定要选择可以安装成功的版本，
 *     主要原因是当前的 RabbitMQ 安装需要一个额外的 wxWidgets 组件，同时还需要考虑 Erlang 的版本。
 *     关于 wxWidgets 组件： <a href="https://wxwidgets.org/downloads/" />
 * <p>
 *     rabbitMQ的几个端口
 *     5672     服务的端口
 *     25672    集群服务的接口
 *     15672    web端的接口
 *     4369     实际开发中，程序所使用的端口
 * @author caimeng
 * @date 2024/7/30 15:00
 */
package com.example.boot3.yootk.amqp;