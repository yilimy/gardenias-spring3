package com.example.boot3.scan.integration.log;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 1. 指定一个实现了 {@link CodeFunctionInterface} 的枚举类，赋值给 {@link this#targetEnum()}
 * 2. 指定扫描包路径给 {@link this#value()}, 如果不指定，读取注解 {@link BusinessLogScan} 同路径下的包路径
 * 3. 根据code {@link CodeFunctionInterface#getCode()}, 匹配出已指定{@link com.example.boot3.scan.enums.ForwardEnum} 枚举同样方法码的 {@link CodeFunctionInterface}
 * 4. 根据已过滤的 {@link CodeFunctionInterface} ，生成动态AOP表达式
 * 5. 表达式不为空，执行bean注入 {@link BusinessSystemImportBeanDefinitionRegistrar#registerBean(BeanDefinitionRegistry, String)}
 *
 * @author caimeng
 * @date 2024/5/23 13:59
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Import(BusinessSystemImportBeanDefinitionRegistrar.class)
public @interface BusinessLogScan {
    String value() default "";

    Class<?> targetEnum() default Object.class;

}
