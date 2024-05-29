package com.example.mybatis.spring.main;

import com.example.mybatis.spring.mapper.UserMapper;
import com.example.mybatis.spring.service.UserService;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.io.InputStream;


@ComponentScan("com.example.mybatis.spring")
//@Import(MybatisImportBeanDefinitionRegistrar.class)
//@SpringBootApplication
public class MainApplication2 {

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception{
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        return new SqlSessionFactoryBuilder().build(inputStream);
    }

    @Bean
    public UserMapper userMapper(SqlSessionFactory sqlSessionFactory) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSessionFactory.getConfiguration().addMapper(UserMapper.class);
        return sqlSession.getMapper(UserMapper.class);
    }

    public static void main(String[] args) {
//        SpringApplication.run(SpringMybatisDemoApplication.class, args);
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(MainApplication2.class);
        applicationContext.refresh();

        UserService userService = applicationContext.getBean("userService", UserService.class);
        userService.test();
    }

}
