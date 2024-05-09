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
 * ContextConfiguration 只能在test中使用，
 * 因为org.springframework.boot:spring-boot-starter-test设置了scope=test
 * ExtendWith 会启动一个单独用于测试的容器
 * <a href="https://blog.csdn.net/ll1042668699/article/details/128069286">@ExtendWith注解</a>
 * @author caimeng
 * @date 2024/5/9 23:31
 */
@Slf4j
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath:/spring/spring-aop.xml"})
public class AopXmlStartTest {
    @Autowired
    private IMessageService messageService;
    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 这里要使用{@link org.junit.jupiter.api.Test}
     * 普通{@link org.junit.Test}执行时，messageService为空
     * spring的代理模式是由 proxy-target-class 来控制的(JDK or CgLib) - xml
     * 注解的方式也有对应的target属性来赋值
     */
    @Test
    public void testEcho() {
        log.info("【ECHO调用】{}", this.messageService.echo("为美好的世界献上祝福"));
        log.info("applicationContext 的实现类: {}", applicationContext.getClass().getName());
        // 为什么这个Bean的名字是IMessageServiceImpl，而不是iMessageServiceImpl
        log.info("IMessageService 的实现类: {}", applicationContext.getBean("IMessageServiceImpl").getClass().getName());
    }
}
