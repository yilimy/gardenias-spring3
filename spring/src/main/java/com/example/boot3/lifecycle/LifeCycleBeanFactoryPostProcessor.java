package com.example.boot3.lifecycle;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * 打印bean生命周期的日志
 * @author caimeng
 * @date 2024/4/15 14:43
 */
@Slf4j
@Component
public class LifeCycleBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        log.info("单例bean: {}", Arrays.toString(beanFactory.getSingletonNames()));
    }

}
