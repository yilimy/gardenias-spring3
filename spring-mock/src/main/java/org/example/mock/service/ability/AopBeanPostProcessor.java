package org.example.mock.service.ability;

import org.example.mock.spring.IBeanPostProcessor;
import org.example.mock.spring.IComponent;
import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.framework.DefaultAopProxyFactory;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;

/**
 * 创建一个aop的后置处理器
 * 简单Cglib代理
 * 为了方便，统一创建Cglib，因为JDK要接口支持
 * @see DefaultAopProxyFactory#createAopProxy(AdvisedSupport)
 * @see org.springframework.aop.framework.CglibAopProxy#CglibAopProxy(AdvisedSupport)
 * @see org.springframework.cglib.proxy.MethodInterceptor
 * @author caimeng
 * @date 2024/6/13 18:52
 */
@IComponent
public class AopBeanPostProcessor implements IBeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        // 代理 AopService
        if ("aopSimpleService".equals(beanName)) {
            return Enhancer.create(bean.getClass(), (MethodInterceptor) (obj, method, args, proxy) -> {
                // 前置任务
                System.out.println("Cglib before ...");
                // 用子类对象（代理对象obj）去调用超类方法(proxy)
                Object result = proxy.invokeSuper(obj, args);
                // 后置任务
                System.out.println("Cglib after ...");
                return result;
            });
        }
        return bean;
    }
}
