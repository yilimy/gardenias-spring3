package com.example.boot3.valid.config;

import org.hibernate.validator.HibernateValidator;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.SpringConstraintValidatorFactory;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * 快速失败校验的配置
 * @author caimeng
 * @date 2024/5/17 18:37
 */
@Configuration
public class FastFailedValidConfig {
    /**
     * 自定义的快速失败校验器
     * 参考: <a href="https://blog.csdn.net/wangpengfei_p/article/details/122949239"></a>
     * @see SpringConstraintValidatorFactory#SpringConstraintValidatorFactory(AutowireCapableBeanFactory)
     * <p>
     *     AutowireCapableBeanFactory是在BeanFactory的基础上实现对已存在实例的管理。
     *     可以使用这个接口集成其他框架，捆绑并填充并不由Spring管理生命周期并已存在的实例。
     *     <a href="https://www.jianshu.com/p/14dd69b5c516"></a>
     * @param beanFactory 自动注入bean工厂
     * @return 校验器
     */
    @Bean
    public Validator fastFailedValidator(AutowireCapableBeanFactory beanFactory) {
        try (ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
                .configure()
                // 快速失败
                .failFast(true)
                // 解决spring依赖注入
                .constraintValidatorFactory(new SpringConstraintValidatorFactory(beanFactory))
                .buildValidatorFactory()) {
            return validatorFactory.getValidator();
        }
    }
}
