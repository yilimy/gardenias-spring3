package com.example.amqp.producer.service;

/**
 * 生产端发送消息的服务
 * @author caimeng
 * @date 2024/8/14 18:11
 */
public interface IMessageService {

    /**
     * 发送消息的接口
     * @param msg 消息体
     */
    void send(String msg);
}
