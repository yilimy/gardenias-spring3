package com.example.boot3.aop.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 放弃xml配置，使用注解方式启动aop
 * 从xml的schema中，查到 aspectj-autoproxy 的核心java类是 {@link org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator}
 * 使用注解{@link org.springframework.context.annotation.EnableAspectJAutoProxy}开启aop功能
 * @author caimeng
 * @date 2024/5/13 14:40
 */
// 启用aop配置环境
@EnableAspectJAutoProxy
// 设置扫描路径
@ComponentScan({"com.example.boot3.aop.service", "com.example.boot3.aop.advice"})
public class AopConfig {
}
