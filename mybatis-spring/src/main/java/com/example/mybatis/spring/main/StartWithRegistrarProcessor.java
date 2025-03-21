package com.example.mybatis.spring.main;

import com.example.mybatis.spring.config.BeanRegistryConfig;
import org.mybatis.spring.MapperScan;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 使用 BeanDefinitionRegistrar 后置处理器来实现对Mybatis的整合
 * <p>
 *     相对于 {@link StartWithBeanDefinitionRegistrar} 使用 {@link ImportBeanDefinitionRegistrar} 的方式，
 *     使用 {@link BeanDefinitionRegistryPostProcessor} 后置处理器的方式，没有 {@link AnnotationMetadata} 来提供注解元数据，
 *     因此扫描路径的传入，需要手动指定或者通过配置文件读取。
 * @author caimeng
 * @date 2025/3/20 16:01
 */
@ComponentScan(value = {"com.example.mybatis.spring", "org.mybatis.spring.factory"})
@Import(BeanRegistryConfig.class)
@MapperScan("com.example.mybatis.spring.mapper")
public class StartWithRegistrarProcessor {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(StartWithRegistrarProcessor.class);
        context.refresh();
        // org.apache.ibatis.binding.MapperProxy@4ef782af
        Object userMapper = context.getBean("userMapper");
        System.out.println(userMapper);
        // org.mybatis.spring.MybatisFactoryBean@11d8ae8b
        Object userMapper2 = context.getBean("&userMapper");
        System.out.println(userMapper2);
    }
}
