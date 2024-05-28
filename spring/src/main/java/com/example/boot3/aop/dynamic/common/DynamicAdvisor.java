package com.example.boot3.aop.dynamic.common;

import lombok.Getter;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.Pointcut;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;

import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * @author caimeng
 * @date 2024/5/28 11:28
 */
public class DynamicAdvisor extends AbstractPointcutAdvisor {
    @Getter
    private final Pointcut pointcut;
    @Getter
    private final Advice advice;

    /**
     * 拦截器 {@link MethodInterceptor} 和 {@link org.springframework.aop.BeforeAdvice} 走的是 Advice 的不同的实现
     * @param annotationClass 注解
     * @param interceptor 拦截器
     */
    public DynamicAdvisor(Class<? extends Annotation> annotationClass, MethodInterceptor interceptor) {
        this.pointcut = buildPointcut(annotationClass);
        this.advice = interceptor;
    }

    public DynamicAdvisor(String expression, MethodInterceptor interceptor) {
        this.pointcut = buildPointcut(expression);
        this.advice = interceptor;
    }

    /**
     * 根据注解构建 Pointcut
     * 参考自： @Async 中的 {@link org.springframework.scheduling.annotation.AsyncAnnotationAdvisor#buildPointcut(Set)}
     * @return Pointcut
     */
    private Pointcut buildPointcut(Class<? extends Annotation> annotationClass) {
        Pointcut cpc = new AnnotationMatchingPointcut(annotationClass, true);
        Pointcut mpc = new AnnotationMatchingPointcut(null, annotationClass, true);
        ComposablePointcut result = new ComposablePointcut(cpc);
        result.union(cpc);
        result = result.union(mpc);
        return result;
    }

    /**
     * 根据表达式构建 Pointcut
     * @param expression 切面表达式
     * @return Pointcut
     */
    private Pointcut buildPointcut(String expression) {
        AspectJExpressionPointcut expressionPointcut = new AspectJExpressionPointcut();
        expressionPointcut.setExpression(expression);
        return expressionPointcut;
    }
}
