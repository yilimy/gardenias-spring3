package com.example.dubbo.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动如果出现java版本差异,添加jvm启动参数:
 * --add-opens java.base/java.lang=ALL-UNNAMED
 * java.lang 为不兼容的包路径
 * @author caimeng
 * @date 2024/4/18 10:52
 */

@SpringBootApplication
public class ProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProviderApplication.class, args);
    }

}
