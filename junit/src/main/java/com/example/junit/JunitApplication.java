package com.example.junit;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.InputStream;

/**
 * @author caimeng
 * @date 2024/6/20 18:24
 */
@EnableTransactionManagement
@MapperScan("com.example.junit.mockito.mapper")
@SpringBootApplication
public class JunitApplication {
    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception{
        InputStream inputStream = Resources.getResourceAsStream("mybatis/mybatis-config.xml");
        return new SqlSessionFactoryBuilder().build(inputStream);
    }
}
