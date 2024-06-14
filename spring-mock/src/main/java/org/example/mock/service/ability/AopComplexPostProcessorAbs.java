package org.example.mock.service.ability;

import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.framework.AbstractAdvisingBeanPostProcessor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator;
import org.springframework.aop.support.AopUtils;

/**
 * 处理 SingletonTargetSource 以及拦截链
 * @deprecated 未完成的实现
 * @author caimeng
 * @date 2024/6/14 16:51
 */
@Deprecated
public abstract class AopComplexPostProcessorAbs {
    abstract void before();
    abstract void after();

    /**
     * 多个代理的处理
     * 参考
     *      {@link AbstractAdvisingBeanPostProcessor#postProcessAfterInitialization(Object, String)}
     *      {@link AbstractAutoProxyCreator#postProcessAfterInitialization(Object, String)}
     * @param bean 单例bean或代理对象
     * @param beanName 单例bean的名字
     * @return 代理对象
     */
    protected Object postProcessAfterInitialization(Object bean, String beanName) {
        if ("aopComplexService".equals(beanName)) {
            // 这里不好手写，直接搬 spring-aop 的类来用
            ProxyFactory proxyFactory = new ProxyFactory();
            // 持有同一个 SingletonTargetSource
            if (AopUtils.isAopProxy(bean)) {
                // 如果 bean 是代理对象
                Class<?> targetClass = AopUtils.getTargetClass(bean);
                //TODO 如何设置 targetBean, 以及如何设置拦截链？
            } else {
                proxyFactory.setTarget(bean);
            }
            // JDK动态代理时使用
//            proxyFactory.addInterface();
            /*
             * 和 Cglib 使用的同名类，包名不一样
             * this:  org.aopalliance.intercept.MethodInterceptor
             * cglib: org.springframework.cglib.proxy.MethodInterceptor
             */
            proxyFactory.addAdvice((MethodInterceptor) invocation -> {
                // 前置任务
                before();
                // 用子类对象（代理对象obj）去调用超类方法(proxy)
                Object result = invocation.proceed();
                // 后置任务
                after();
                return result;
            });
            return proxyFactory.getProxy();
        }
        return bean;
    }
}
