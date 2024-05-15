package com.example.dubbo.consumer.service;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 *
 * @author caimeng
 * @date 2024/5/15 17:44
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class DubboDemoServiceTest {

    /**
     * @deprecated 报错,找不到 IOrderService,应该是JVM配置没有生效
     * 因为java版本差异,导致dubbo序列化问题,
     * 依赖库要求jdk8,当前项目是jdk17,
     * <p>
     *     需要添加JVM参数做兼容:
     *     --add-opens java.base/java.lang=ALL-UNNAMED  (provider)
     *     --add-opens java.base/java.math=ALL-UNNAMED  (provider, consumer)
     * </p>
     */
    @Deprecated
    @Test
    public void initOrderTest() {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext("com.example.dubbo.consumer.service");
        DubboDemoService bean = context.getBean(DubboDemoService.class);
        bean.initOrder("userId");
    }
}
