package com.example.boot3.aop.advice;

import lombok.extern.slf4j.Slf4j;

/**
 * AOP的早先实现与现在是有区别的
 * 早先使用一系列接口实现（Advice）
 * 在 spring 1.2(?) 通过注解的方式实现AOP
 * 版本上有差异
 * resource/spring/spring-aop.xml
 * 最原始的实现机制{@link org.aopalliance.aop.Advice}
 *
 * @author caimeng
 * @date 2024/5/9 22:37
 */
@Slf4j
public class ServiceAdvice {

    /**
     * 早先实现 BeforeAdvice
     * @see org.springframework.aop.BeforeAdvice
     */
    public void beforeHandle(String msg) {
        log.info("启用业务功能前置处理, 方法的参数:{}", msg);
    }

    /**
     * 早先实现 AfterAdvice
     * @see org.springframework.aop.AfterAdvice
     */
    public void afterHandle(String msg) {
        log.info("启用业务功能后置处理, 方法的参数:{}", msg);
    }

    public void afterReturningHandle(String value) {
        log.info("启用业务功能后置返回通知, value:{}", value);
    }

    public void afterThrowHandle(Exception e) {
        log.info("启用业务功能后置异常处理, 异常信息:{}", e.getMessage());
    }
}
