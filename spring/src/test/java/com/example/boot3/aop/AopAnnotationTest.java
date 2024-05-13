package com.example.boot3.aop;

import com.example.boot3.aop.main.AopAnnotationApplicationContext;
import com.example.boot3.aop.service.IMessageService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * 基于注解的AOP实现
 * 第三代模型，第二代 {@link AopXmlStartAroundTest}
 * 基于XML配置模型，一定要做的配置项与处理项相统一，但是实际应用过程中很难做到统一
 * 所以为了简化AOP的设计实现，实际开发中几乎都是基于注解的方式处理定义
 * 完全不使用xml，参考 {@link AopAnnotationApplicationContext}
 * @author caimeng
 * @date 2024/5/10 14:14
 */
@Slf4j
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath:/spring/spring-aop-annotation.xml"})
public class AopAnnotationTest {
    @Autowired
    private IMessageService messageService;
    @Test
    public void testEchoNormal() {
        log.info("【ECHO调用】{}", this.messageService.echo("Hello, 为美好的世界献上祝福"));
    }
}
