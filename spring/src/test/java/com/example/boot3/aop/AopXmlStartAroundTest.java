package com.example.boot3.aop;

import com.example.boot3.aop.service.IMessageService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * AOP 的第二代模型
 * 第一代 {@link AopXmlStartTest}
 * @author caimeng
 * @date 2024/5/9 23:31
 */
@Slf4j
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath:/spring/spring-aop-around.xml"})
public class AopXmlStartAroundTest {
    @Autowired
    private IMessageService messageService;
    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void testEchoNormal() {
        log.info("【ECHO调用】{}", this.messageService.echo("Hello, 为美好的世界献上祝福"));
        log.info("applicationContext 的实现类: {}", applicationContext.getClass().getName());
        log.info("IMessageService 的实现类: {}", applicationContext.getBean("IMessageServiceImpl").getClass().getName());
    }

    @Test
    public void testEchoException() {
        log.info("【ECHO调用】{}", this.messageService.echo("为美好的世界献上祝福"));
        log.info("applicationContext 的实现类: {}", applicationContext.getClass().getName());
        // 为什么这个Bean的名字是IMessageServiceImpl，而不是iMessageServiceImpl
        log.info("IMessageService 的实现类: {}", applicationContext.getBean("IMessageServiceImpl").getClass().getName());
    }


}
