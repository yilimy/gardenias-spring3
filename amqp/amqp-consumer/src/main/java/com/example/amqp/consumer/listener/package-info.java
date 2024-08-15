/**
 * 消息监听器
 * <p>
 *     使用bean方式配置的消息监听器
 *          {@link com.example.amqp.consumer.listener.RabbitMQMessageListener}
 *     接收消息结构体的方式
 *          {@link com.example.amqp.consumer.listener.AnnotationMessageListener}
 *     接收字符串的方式
 *          {@link com.example.amqp.consumer.listener.AnnotationStringListener}
 *     接收对象的方式
 *          {@link com.example.amqp.consumer.listener.AnnotationObjectListener}
 * <p>
 *     当可以通过消息组件进行对象传输的时候，实际上就形成了一个简单的分布式应用了。
 *     在应付高并发处理的过程中，消息组件可以起到良好的缓冲作用，而所有的操作数据通过对象可以方便的进行传输和业务处理。
 * @author caimeng
 * @date 2024/8/15 11:14
 */
package com.example.amqp.consumer.listener;