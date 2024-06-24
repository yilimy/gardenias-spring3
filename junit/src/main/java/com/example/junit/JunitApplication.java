package com.example.junit;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author caimeng
 * @date 2024/6/20 18:24
 */
@EnableTransactionManagement
@MapperScan("com.example.junit.mockito.mapper")
@SpringBootApplication
public class JunitApplication {
}
