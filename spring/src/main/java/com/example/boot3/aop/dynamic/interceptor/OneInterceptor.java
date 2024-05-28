package com.example.boot3.aop.dynamic.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @author caimeng
 * @date 2024/5/28 14:23
 */
public class OneInterceptor implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        // 简单测试的方法返回值都是字符串
        Object object = invocation.proceed();
        return object + " 增强-One";
    }
}
