package com.example.mybatis.spring.main;

import com.example.mybatis.spring.service.UserService;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.mybatis.spring.factory.OrderFactoryBean;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.io.InputStream;


@ComponentScan("com.example.mybatis.spring")
//@Import(MybatisImportBeanDefinitionRegistrar.class)
//@SpringBootApplication
public class MainApplication4 {

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception{
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        return new SqlSessionFactoryBuilder().build(inputStream);
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(MainApplication4.class);
//        applicationContext.refresh();
        // ====== orderMapper ======
        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition().getBeanDefinition();
        beanDefinition.setBeanClass(OrderFactoryBean.class);
        applicationContext.registerBeanDefinition("orderMapper", beanDefinition);
        applicationContext.refresh();

        Object order = applicationContext.getBean("orderMapper");
        // org.apache.ibatis.binding.MapperProxy@6a192cfe
        System.out.println(order);
        Object order2 = applicationContext.getBean("&orderMapper");
        // com.example.spring.factory.OrderFactoryBean@5119fb47
        System.out.println(order2);

        UserService userService = applicationContext.getBean("userService", UserService.class);
        userService.testOrder();
    }
}
