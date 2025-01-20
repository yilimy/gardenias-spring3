package com.gardenia;   // 父包，这个包中所有子包的类会被自动扫描

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author caimeng
 * @date 2025/1/20 11:25
 */
@SpringBootApplication  // 一个注解解决所有的问题
public class StartSpringBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(StartSpringBootApplication.class, args);
    }
}
