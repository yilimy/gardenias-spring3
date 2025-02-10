package com.gardenia.web.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author caimeng
 * @date 2025/2/10 10:52
 */
@Component
@Aspect
public class ServiceAspect {    // 实现aop的切面管理

    @Around("execution(* com.gardenia.web.service.*.*(..))")
    public Object aroundInvoke(ProceedingJoinPoint joinPoint) throws Throwable {
        // 在方法执行前的操作
        System.out.println("【ServiceInvokeBefore】执行参数: " + Arrays.toString(joinPoint.getArgs()));

        // 执行目标方法
        Object result = joinPoint.proceed();

        // 在方法执行后的操作
        System.out.println("【ServiceInvokeAfter】返回结果: " + result);

        return result;
    }
}
