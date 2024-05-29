package com.example.mybatis.spring.main;

import com.example.mybatis.spring.service.UserService;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.mybatis.spring.MapperScan;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.io.InputStream;

@ComponentScan("com.example.mybatis.spring")
// 添加了 MapperScan 理论上会扫描指定包路径下的所有Mapper接口，不需要在添加@Mapper注解
@MapperScan("com.example.mybatis.spring.mapper")
public class MainApplication {

    /**
     * 配置一个 SqlSessionFactory
     * SqlSessionFactory 的落脚点也是配置一个DataSource
     * {@link SqlSessionFactory#getConfiguration()}
     * {@link org.apache.ibatis.mapping.Environment#getDataSource()}
     * @return SqlSessionFactory
     * @throws Exception Exception
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception{
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        return new SqlSessionFactoryBuilder().build(inputStream);
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(MainApplication.class);
        applicationContext.refresh();

        // No qualifying bean of type 'com.example.spring.mapper.UserMapper' available
        UserService userService = applicationContext.getBean("userService", UserService.class);
        userService.test();

//        UserService userService = applicationContext.getBean("userService", UserService.class);
        userService.testOrder();
    }
}
