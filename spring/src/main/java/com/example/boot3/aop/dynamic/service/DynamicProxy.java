package com.example.boot3.aop.dynamic.service;

import com.example.boot3.aop.dynamic.common.DynamicAdvisor;
import com.example.boot3.aop.dynamic.common.OperatorEventEnum;
import jakarta.annotation.Nonnull;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.framework.ProxyProcessorSupport;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.stereotype.Component;

/**
 * @author caimeng
 * @date 2024/5/28 11:54
 */
@Component
public class DynamicProxy extends ProxyProcessorSupport implements BeanFactoryAware {
    private DefaultListableBeanFactory beanFactory;
    @Override
    public void setBeanFactory(@Nonnull BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
    }

    public void operateAdvisor(DynamicAdvisor advisor, OperatorEventEnum operator) {
        for (String beanName : beanFactory.getBeanDefinitionNames()) {
            // 循环所有的bean名称
            Object bean = beanFactory.getBean(beanName);
            if (!isEligible(bean, advisor)) {
                // 没有被切面影响的bean将被跳过
                continue;
            }
            /*
             * 判断当前的bean是否已经是代理对象。
             * 如果已经是代理对象，直接进行 Advisor 操作
             */
            if (bean instanceof Advised advised) {
                if (operator == OperatorEventEnum.DEL) {
                    advised.removeAdvisor(advisor);
                } else if (operator == OperatorEventEnum.ADD) {
                    advised.addAdvisor(advisor);
                }
                continue;
            }
            // 生成 advisor 的代理对象
            ProxyFactory proxyFactory = new ProxyFactory();
            proxyFactory.addAdvisor(advisor);
            proxyFactory.setTarget(bean);
            ClassLoader proxyClassLoader = this.getProxyClassLoader();
            Object proxy = proxyFactory.getProxy(proxyClassLoader);
            // 销毁之前的bean，把新的bean放到容器
            beanFactory.destroySingleton(beanName);
            beanFactory.registerSingleton(beanName, proxy);
        }
    }

    /**
     * 判断bean是否应用到了切面
     * @param bean 待测试的类
     * @param advisor 切面工具
     * @return 是否应用了切面
     */
    private boolean isEligible(Object bean, Advisor advisor) {
        return AopUtils.canApply(advisor, bean.getClass());
    }
}
