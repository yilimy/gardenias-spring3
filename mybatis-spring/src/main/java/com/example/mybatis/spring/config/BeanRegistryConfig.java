package com.example.mybatis.spring.config;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.mybatis.spring.MybatisBeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;

/**
 * @author caimeng
 * @date 2025/3/20 16:26
 */
@Configuration
public class BeanRegistryConfig {
    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception{
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        return new SqlSessionFactoryBuilder().build(inputStream);
    }

    @Bean
    public MybatisBeanDefinitionRegistryPostProcessor mybatisBeanDefinitionRegistryPostProcessor() {
        MybatisBeanDefinitionRegistryPostProcessor postProcessor = new MybatisBeanDefinitionRegistryPostProcessor();
        postProcessor.setBasePackage("com.example.mybatis.spring.mapper");
        return postProcessor;
    }
}
