package com.example.boot3.aop.advice;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * AOP 基于注解的实现
 * @author caimeng
 * @date 2024/5/9 22:37
 */
@Slf4j
// 需要明确的告诉spring容器，这是一个aop的程序类
@Aspect
// 此时进行的是aop的代理，用Component也行，被spring扫描到容器就行
//@Configuration
@Component
public class ServiceAnnotationAdvice {

    // 定义一个公共的切面
    @Pointcut("execution(public * com.example..service..*.*(..))")
    public void pointCut(){}

    @Before(argNames = "msg",
            value = "execution(public * com.example..service..*.*(..)) && args(msg)")
    public void beforeHandle(String msg) {
        log.info("annotation 启用业务功能前置处理, 方法的参数:{}", msg);
        if (!msg.contains("Hello")) {
            throw new UnsupportedOperationException("没有打招呼，所以跑路了");
        }
    }

    @AfterReturning(value = "pointCut()", argNames = "value", returning = "value")
    public void afterReturningHandle(String value) {
        log.info("annotation 启用业务功能后置返回通知, value:{}", value);
    }

    @After(argNames = "msg",
            value = "execution(public * com.example..service..*.*(..)) && args(msg)")
    public void afterHandle(String msg) {
        log.info("annotation 启用业务功能后置处理, 方法的参数:{}", msg);
    }

    @AfterThrowing(value = "pointCut()", argNames = "e", throwing = "e")
    public void afterThrowHandle(Exception e) {
        log.info("annotation 启用业务功能后置异常处理, 异常信息:{}", e.getMessage());
    }

    @Around("pointCut()")
    public Object handleRound(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("【annotation 环绕通知处理】 目标对象: {}", joinPoint.getTarget());
        log.info("【annotation 环绕通知处理】 对象的类型: {}", joinPoint.getKind());
        log.info("【annotation 环绕通知处理】 切面表达式: {}", joinPoint.getStaticPart());
        log.info("【annotation 环绕通知处理】 方法签名: {}", joinPoint.getSignature());
        log.info("【annotation 环绕通知处理】 源代码定位: {}", joinPoint.getSourceLocation());
        log.info("【annotation 环绕通知前置处理】 业务方法调用前，传递的参数: {}", Arrays.toString(joinPoint.getArgs()));
        Object returnObj = null;
        /*
         * 这里不设置参数应该也可以，会默认使用原参数。
         * 有参数的方法是为了参数值更新时使用。
         */
        try {
            returnObj = joinPoint.proceed(joinPoint.getArgs());
        } catch (Exception e) {
            // 按正常的流程，应该抛出该异常
            log.info("【annotation 异常通知】 业务方法产生异常，异常信息: {}", e.getMessage());
            throw e;
        }
        log.info("【annotation 返回通知】 业务方法执行完毕，返回方法处理结果: {}", returnObj);
        return returnObj;
    }
}
