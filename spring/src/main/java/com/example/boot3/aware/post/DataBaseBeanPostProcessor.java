package com.example.boot3.aware.post;

import com.example.boot3.aware.bind.IDataBaseAware;
import com.example.boot3.aware.source.DataBaseConfig;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Objects;

/**
 * @author caimeng
 * @date 2024/4/16 14:31
 */
public class DataBaseBeanPostProcessor implements BeanPostProcessor, ApplicationContextAware {
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Object config = this.applicationContext.getBean("dataBaseConfig");
        // 已经在容器中注册
        if (Objects.isNull(config)) {
            return bean;    // 直接返回当前的Bean
        }
        // IDataBaseAware 接口子类对象实例中，一定要注入DataBaseConfig对象
        if (config instanceof DataBaseConfig && bean instanceof IDataBaseAware) {
            // 依赖配置
            ((IDataBaseAware) bean).setDataBaseConfig((DataBaseConfig) config);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }
}
