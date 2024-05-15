package com.example.boot3.processor.roadjava;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * 原理：{@link org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory#initializeBean(java.lang.Object, java.lang.String)}
 * 应用如：
 * {@link org.springframework.context.support.ApplicationContextAwareProcessor} ApplicationContextAware的原理
 * {@link AutowiredAnnotationBeanPostProcessor} 使用@Autowired注入的原理
 * {@link CommonAnnotationBeanPostProcessor} 使用jsr250原理（@Resource，@PostConstruct等）
 * aop原理（@EnableAspectJAutoProxy）
 * <a href="https://www.bilibili.com/video/BV1Mt42177MK/?spm_id_from=333.999.0.0&vd_source=ae63e735dcccc0734ec3b3b043a159f9">[java漫谈系列99]spring扩展点之BeanPostProcessor</a>
 * @author caimeng
 * @date 2024/5/15 9:42
 */
@Component
public class MyBeanPostProcessor implements BeanPostProcessor {
    /**
     * 会在调用每一个bean的初始化方法之前，回调所有的BeanPostProcessor的postProcessBeforeInitialization
     * <p>
     *     三种初始化方法
     *     1. @PostConstruct {@link InitializationTestService#doInit()}
     *     2. afterPropertiesSet {@link InitializationTestService#afterPropertiesSet()}
     *     3. initMethod {@link InitializationTestConfig#initializationTestServiceFromConfig()}
     *
     * @param bean the new bean instance
     * @param beanName the name of the bean
     * @return Object
     * @throws BeansException BeansException
     */
    @Override
    public Object postProcessBeforeInitialization(@NonNull Object bean, @NonNull String beanName) throws BeansException {
        if (bean instanceof InitializationTestService) {
            System.out.printf("bean在spring容器中的名字:%s, bean:%s, before\r\n", beanName, bean);
            /*
             * 对bean做处理或者解析bean
             * e.g. 获取到bean上的配置，进行缓存
             */
        }
        // 返回处理后的bean
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(@NonNull Object bean, @NonNull String beanName) throws BeansException {
        if (bean instanceof InitializationTestService) {
            System.out.printf("bean在spring容器中的名字:%s, bean:%s, after\r\n", beanName, bean);
        }
        return bean;
    }
}
