/**
 * RabbitMQ集群架构
 * <a href="https://www.bilibili.com/video/BV1V8YseKEhL/" />
 * <p>
 *     集群一般是大型互联网公司，或者跨国公司使用。
 *     RabbitMQ在发展过程中，一共提出了四种集群模式，现阶段只使用最后一种。
 *     1. 远程模式。
 *          本地消息数据复制到远程主机，可以实现跨域连接。
 *          在本地服务访问量过高时，远程服务也可以提供支持。
 *          不再建议使用。
 *     2. 主备模式
 *          每个节点保存有各自的交换机，并设置有公共的数据存储空间。
 *          通过HAProxy代理进行节点访问，master节点出现问题后，自动切换到backup节点。
 *          该模式适用于并发量不高的应用场景。
 *          User —— HAProxy —— exchange —— Queue
 *                      |                   |
 *                      ——— —— exchange —— ——
 *          HAProxy 组件拥有监控和负载均衡的能力，同时支持不同的协议。
 *     3. 多活模式
 *          基于Federation插件实现AMQP通讯，连接双方使用不同的账户以及虚拟主机。
 *          该模式下，每一个集群除了要提供正常的业务以外，还要实现部分的消息数据共享。
 *          不是现阶段开发的首选。
 *     4. 镜像模式
 *          集群中的每一个节点都保存有相同的数据信息，如果其中有一个节点出现了问题，其他节点可以继续提供支持。
 *          使用集群时，配置 amqp.rabbit.host 变更为 amqp.rabbit.address 并更改对应的连接配置。
 * @author caimeng
 * @date 2024/8/15 15:25
 */
package com.example.amqp.producer.cluster;