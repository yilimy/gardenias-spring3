package com.example.boot3.aop.advice;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;

import java.util.Arrays;

/**
 * @author caimeng
 * @date 2024/5/10 13:47
 */
@Slf4j
public class ServiceAroundAdvice {

    /**
     * 按照正常的设计来讲，如果业务方法出现了异常，应该是交给调用处进行处理的
     * @param joinPoint 切点
     * @return 方法调用返回
     * @throws Throwable 方法异常
     */
    public Object handleRound(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("【环绕通知处理】 目标对象: {}", joinPoint.getTarget());
        log.info("【环绕通知处理】 对象的类型: {}", joinPoint.getKind());
        log.info("【环绕通知处理】 切面表达式: {}", joinPoint.getStaticPart());
        log.info("【环绕通知处理】 方法签名: {}", joinPoint.getSignature());
        log.info("【环绕通知处理】 源代码定位: {}", joinPoint.getSourceLocation());
        log.info("【环绕通知前置处理】 业务方法调用前，传递的参数: {}", Arrays.toString(joinPoint.getArgs()));
        Object returnObj = null;
        /*
         * 这里不设置参数应该也可以，会默认使用原参数。
         * 有参数的方法是为了参数值更新时使用。
         */
        try {
            returnObj = joinPoint.proceed(joinPoint.getArgs());
        } catch (Exception e) {
            // 按正常的流程，应该抛出该异常
            log.info("【异常通知】 业务方法产生异常，异常信息: {}", e.getMessage());
        }
        log.info("【返回通知】 业务方法执行完毕，返回方法处理结果: {}", returnObj);
        return returnObj;
    }
}
