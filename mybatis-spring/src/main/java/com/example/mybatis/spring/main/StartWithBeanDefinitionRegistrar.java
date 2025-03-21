package com.example.mybatis.spring.main;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.mybatis.spring.MapperScan;
import org.mybatis.spring.MybatisImportBeanDefinitionRegistrar;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import java.io.InputStream;


@ComponentScan(value = {"com.example.mybatis.spring", "org.mybatis.spring.factory"})
@Import(MybatisImportBeanDefinitionRegistrar.class)
@MapperScan("com.example.mybatis.spring.mapper")
public class StartWithBeanDefinitionRegistrar {

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception{
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        return new SqlSessionFactoryBuilder().build(inputStream);
    }

    public static void main(String[] args) {
//        SpringApplication.run(SpringMybatisDemoApplication.class, args);
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(StartWithBeanDefinitionRegistrar.class);
        applicationContext.refresh();

        // ====== userMapper ======
        Object userMapper = applicationContext.getBean("userMapper");
        /*
         * org.apache.ibatis.binding.MapperProxy@4567f35d
         * 获取到的 Mapper 对象，是由mybatis动态代理的对象
         */
        System.out.println(userMapper);
        Object userMapper2 = applicationContext.getBean("&userMapper");
        /*
         * org.mybatis.spring.MybatisFactoryBean@5ffead27
         * 获取到的 userMapper 的 FactoryBean 对象，是mybatis帮我们创建的 MybatisFactoryBean
         * 符号 & 表示获取bean的创建对象 FactoryBean 的实例
         */
        System.out.println(userMapper2);

        // ====== userBean ======
        Object user = applicationContext.getBean("userFactoryBean");
        /*
         * com.example.spring.pojo.User@6356695f
         * 普通spring扫描FactoryBean，获取到的FactoryBean创建的User对象
         */
        System.out.println(user);
        Object user2 = applicationContext.getBean("&userFactoryBean");
        /*
         * com.example.spring.factory.UserFactoryBean@4f18837a
         * 获取到创建User对象的自定义的创建bean对象FactoryBean的实例
         */
        System.out.println(user2);
    }
}
