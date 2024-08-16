package com.example.boot3.threadlocal.tomcat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 测试tomcat多路复用导致的 ThreadLocal 数据泄漏
 * <p>
 *     配置： server.tomcat.threads.max=1
 *          将tomcat最大线程配置成1，那么在第二次访问时就会复用线程，提高复现率
 * @author caimeng
 * @date 2024/8/15 18:16
 */
@SpringBootApplication
public class TomcatThreadLocalApplication {
    public static void main(String[] args) {
        SpringApplication.run(TomcatThreadLocalApplication.class, args);
    }
}
