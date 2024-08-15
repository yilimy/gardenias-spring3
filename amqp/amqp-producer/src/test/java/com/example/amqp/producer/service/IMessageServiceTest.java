package com.example.amqp.producer.service;

import cn.hutool.core.lang.WeightRandom;
import cn.hutool.core.util.RandomUtil;
import com.example.amqp.common.Dept;
import com.example.amqp.producer.StartAMQPProducer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

/**
 * 测试消息发送
 * @author caimeng
 * @date 2024/8/14 18:20
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = StartAMQPProducer.class)
@SpringBootTest(properties = "rabbit.batch.enable=true")
public class IMessageServiceTest {
    @Autowired
    private IMessageService iMessageService;

    /**
     * 测试消息发送
     * <p>
     *     为了方便测试消息发送的内容，建议先启动消费端的监听
     */
    @Test
    public void sendTest() {
        // 消息的发送
        iMessageService.send("沐言科技:www.yootk.com");
    }

    /**
     * 测试，发送一个对象
     */
    @Test
    public void sendObjTest() {
        Dept dept = Dept.builder().deptno(10L).dname("教学研发部").loc("北京").build();
        iMessageService.sendObj(dept);
    }

    /**
     * 发送一定数量的消息
     */
    @Test
    public void sendManyObjTest() {
        List<WeightRandom.WeightObj<String>> nameWeights = Arrays.asList(
                new WeightRandom.WeightObj<>("教学研发部", 0.4),
                new WeightRandom.WeightObj<>("宣传扩张部", 0.3),
                new WeightRandom.WeightObj<>("后勤保障部", 0.3)
        );
        List<WeightRandom.WeightObj<String>> locWeights = Arrays.asList(
                new WeightRandom.WeightObj<>("北京", 0.3),
                new WeightRandom.WeightObj<>("上海", 0.3),
                new WeightRandom.WeightObj<>("广州", 0.3),
                new WeightRandom.WeightObj<>("深圳", 0.1)
        );
        CompletableFuture<Void>[] completableFutures = Stream.generate(
                () ->  Dept.builder()
                        .deptno(RandomUtil.randomLong(1000))
                        .dname(RandomUtil.weightRandom(nameWeights).next())
                        .loc(RandomUtil.weightRandom(locWeights).next())
                        .build())
                .map(dept -> CompletableFuture.runAsync(() -> iMessageService.sendObj(dept)))
                .limit(100)
                .<CompletableFuture<Void>>toArray(CompletableFuture[]::new);
        CompletableFuture.allOf(completableFutures).join();
    }
}
