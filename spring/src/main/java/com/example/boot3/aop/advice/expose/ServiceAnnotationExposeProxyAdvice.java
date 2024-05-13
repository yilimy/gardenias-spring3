package com.example.boot3.aop.advice.expose;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.framework.AopContext;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * AOP 暴露代理实例
 * @author caimeng
 * @date 2024/5/9 22:37
 */
@Slf4j
@Aspect
@Configuration
public class ServiceAnnotationExposeProxyAdvice {
    @Around("execution(* com.example.boot3.aop.service.IMessageService.echo(..))")
    public Object handleRound(ProceedingJoinPoint joinPoint) throws Throwable {
        /*
         * 获取当前代理的实例
         * 在AOP进行真实类代理时，可以通过 exposeProxy=true 来配置当前对象对外暴露，
         * 这样所代理的对象就会保存在AopContext中的ThreadLocal对象实例之中，
         * 开发者就可以在代理类中通过AopContext获取真实类的对象实例。
         * 如果不开启 exposeProxy=true, 则调用AopContext.currentProxy()时会报错
         */
        log.info("【annotation expose 环绕通知处理】 目标对象: {}", AopContext.currentProxy());
        Object returnObj = null;
        /*
         * 这里不设置参数应该也可以，会默认使用原参数。
         * 有参数的方法是为了参数值更新时使用。
         */
        try {
            returnObj = joinPoint.proceed(joinPoint.getArgs());
        } catch (Exception e) {
            // 按正常的流程，应该抛出该异常
            log.info("【annotation expose 异常通知】 业务方法产生异常，异常信息: {}", e.getMessage());
            throw e;
        }
        log.info("【annotation expose 返回通知】 业务方法执行完毕，返回方法处理结果: {}", returnObj);
        return returnObj;
    }
}
