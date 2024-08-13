package com.example.rabbitmq.exchange.fanout;

/**
 * 消费者分组B
 * 相较于 {@link GroupAFanoutConsumerA} 变更了分组和消费方法
 * @author caimeng
 * @date 2024/8/13 10:24
 */
public class GroupCFanoutConsumer extends GroupAFanoutConsumerA {

    public static void main(String[] args) {
        new GroupCFanoutConsumer().firstConsumer();
    }

    /**
     * 变更了组名
     * @return 组名
     */
    @Override
    protected String getGroupQueueName() {
        return "yootk.c.group.queue";
    }

    /**
     * 变更了消费方法
     * @param msg 待消费信息
     */
    @Override
    protected void accept(String msg) {
        System.out.println("【消费组C - fanout - 接收消息】" + msg);
    }
}
