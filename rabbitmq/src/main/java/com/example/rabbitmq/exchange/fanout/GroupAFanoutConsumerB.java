package com.example.rabbitmq.exchange.fanout;

/**
 * 消费者AA的配置镜像，但是有自己的消费方法
 * 相对于 {@link GroupAFanoutConsumerA} 在配置上没有变更
 * <p>
 *     与 {@link GroupAFanoutConsumerA} 是竞争关系，
 *     AA消费掉的消息，AB不会再消费
 * @author caimeng
 * @date 2024/8/8 18:21
 */
public class GroupAFanoutConsumerB extends GroupAFanoutConsumerA {
    public static void main(String[] args) {
        new GroupAFanoutConsumerB().firstConsumer();
    }

    /**
     * 变更了接收到消息后的执行方法
     * @param msg 接收到的消息
     */
    @Override
    protected void accept(String msg) {
        System.out.println("【消费组A - fanout-B - 接收消息】" + msg);
    }
}
