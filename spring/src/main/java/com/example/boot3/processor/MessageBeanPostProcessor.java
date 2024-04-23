package com.example.boot3.processor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @author caimeng
 * @date 2024/4/15 13:58
 */
@Slf4j
@Component
public class MessageBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        log.info("postProcessBeforeInitialization - Bean初始化之前， bean={}, bean={}", bean, beanName);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        log.info("postProcessAfterInitialization - Bean初始化之后， bean={}, bean那={}", bean, beanName);
        return bean;
    }
}
