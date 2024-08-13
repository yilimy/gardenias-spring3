package com.example.rabbitmq.exchange.fanout;

/**
 * 消费者分组B
 * 相较于 {@link GroupAFanoutConsumerA} 变更了分组和消费方法
 * <p>
 *     与组A是平行消息；
 *     组A中的所有消息，都会在组B中消费；
 *     组A中的所有消息是指组A中所有消费者消息的总和
 * <p>
 *     消费关系图：
 *     exchange ——  groupA  ——  consumerA
 *              |           |
 *              |           ——  consumerB
 *              |
 *              ——  groupB
 * @author caimeng
 * @date 2024/8/13 10:24
 */
public class GroupBFanoutConsumer extends GroupAFanoutConsumerA {

    public static void main(String[] args) {
        new GroupBFanoutConsumer().firstConsumer();
    }

    /**
     * 变更了组名
     * @return 组名
     */
    @Override
    protected String getGroupQueueName() {
        return "yootk.b.group.queue";
    }

    /**
     * 变更了消费方法
     * @param msg 待消费信息
     */
    @Override
    protected void accept(String msg) {
        System.out.println("【消费组B - fanout - 接收消息】" + msg);
    }
}
