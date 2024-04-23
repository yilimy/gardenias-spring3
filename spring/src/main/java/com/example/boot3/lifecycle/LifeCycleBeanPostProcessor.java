package com.example.boot3.lifecycle;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @author caimeng
 * @date 2024/4/15 14:45
 */
@Slf4j
@Component
public class LifeCycleBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if ("messageBean".equals(beanName)) {
            // getNestHost(): java11为访问控制而引入的基于类的宿主主机概念
            log.info("[BeanPostProcessor - postProcessBeforeInitialization()] {}", bean.getClass().getNestHost());
        }
        // 返回的bean对象，有可能是处理后的bean对象
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if ("messageBean".equals(beanName)) {
            log.info("[BeanPostProcessor - postProcessAfterInitialization()] {}", bean.getClass().getNestHost());
        }
        return bean;
    }
}
