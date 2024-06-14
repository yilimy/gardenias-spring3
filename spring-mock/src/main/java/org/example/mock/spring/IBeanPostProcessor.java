package org.example.mock.spring;


/**
 * bean后置处理器
 * 参考 {@link org.springframework.beans.factory.config.BeanPostProcessor}
 * @author caimeng
 * @date 2024/6/13 17:37
 */
public interface IBeanPostProcessor {
    default Object postProcessBeforeInitialization(Object bean, String beanName) {
        return bean;
    }

    default Object postProcessAfterInitialization(Object bean, String beanName) {
        return bean;
    }
}
