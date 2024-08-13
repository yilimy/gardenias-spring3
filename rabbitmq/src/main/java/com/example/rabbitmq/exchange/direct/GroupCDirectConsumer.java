package com.example.rabbitmq.exchange.direct;

/**
 * 该消费者配置为消费者B的镜像，但是使用了不同于消费组B的组名
 * @author caimeng
 * @date 2024/8/13 14:22
 */
public class GroupCDirectConsumer extends GroupBDirectConsumer {

    public static void main(String[] args) {
        new GroupCDirectConsumer().firstConsumer();
    }

    /**
     * 消费方法
     */
    @Override
    protected void accept(String msg) {
        System.out.println("【消费组C - direct - 接收消息】" + msg);
    }

    /**
     * 重新定义组名
     * @return 组名
     */
    @Override
    protected String getGroupQueueName() {
        return "yootk.c.group.queue";
    }
}
