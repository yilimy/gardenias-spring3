package com.gardenia.autoconfig.config;

import com.gardenia.autoconfig.vo.DeptAutoConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * 自动装配类
 * <p>
 *     使用  EnableConfigurationProperties 的注入
 * @author caimeng
 * @date 2025/5/15 15:49
 */
@Configuration  // Bean注入
// Dept 的 bean名称定义：前缀 + 横杠 + 类全名
@EnableConfigurationProperties(DeptAutoConfig.class)    // 希望帮助找到配置的属性
public class YootkConfiguration {

    @Bean(name = "books")
    public List<String> getBookList() { // 自定义Bean对象
        return List.of(
                "Java面向对象编程", "Java就业编程实战",
                "JavaWEB就业编程实战", "Spring就业编程实战",
                "SpringBoot就业编程实战", "SpringCloud就业编程实战");
    }

    @Bean
    public String testLog() {
        // 简单测试 YootkConfiguration 配置被正常加载，没有实际意义
        System.out.println("✅ YootkConfiguration loaded!");
        return "loaded";
    }
}
