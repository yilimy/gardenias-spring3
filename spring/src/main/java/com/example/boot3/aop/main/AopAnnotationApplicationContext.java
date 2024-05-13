package com.example.boot3.aop.main;

import com.example.boot3.aop.config.AopConfig;
import com.example.boot3.aop.config.AopConfig4ExposeProxy;
import com.example.boot3.aop.service.IMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author caimeng
 * @date 2024/5/13 14:46
 */
@Slf4j
public class AopAnnotationApplicationContext {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                // 构造方法定义配置类，可以直接刷新上下文
//                new AnnotationConfigApplicationContext(AopConfig.class);
                // 开启了 exposeProxy 配置
                new AnnotationConfigApplicationContext(AopConfig4ExposeProxy.class);
        IMessageService messageService = context.getBean(IMessageService.class);
        messageService.echo("Hello, 为美好的世界献上祝福");
    }
}
