package com.gardenia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author caimeng
 * @date 2025/1/17 16:56
 */
@RestController     // SpringMVC中的注解
@EnableAutoConfiguration    // SpringBoot扩展的注解, 自动装配注解
public class FirstSpringBootApplication {   // 编写的第一个SpringBoot程序
    @RequestMapping("/")    // 映射目录
    String home() {
        return "Hello World!";
    }   // 响应的数据信息

    public static void main(String[] args) {
        /*
         * 2025-01-20T10:19:13.258+08:00  INFO 11056 --- [           main] c.gardenia.FirstSpringBootApplication    :
         *      Starting FirstSpringBootApplication using Java 17.0.10 with PID 11056 (D:\Gardenias\SpringBoot3Demo\firstboot\target\classes started by EDY in D:\Gardenias\SpringBoot3Demo)
         *      > FirstSpringBootApplication : 启动类
         *      > Java 17.0.10 : 使用的java版本
         *      > PID 11056 : 进程ID
         * 2025-01-20T10:19:13.262+08:00  INFO 11056 --- [           main] c.gardenia.FirstSpringBootApplication    :
         *      No active profile set, falling back to 1 default profile: "default"
         *      > profile : 使用的配置文件
         * 2025-01-20T10:19:14.009+08:00  INFO 11056 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  :
         *      Tomcat initialized with port(s): 8080 (http)
         *      > 启动容器为 tomcat, 绑定的端口号为 8080, 使用的协议为 http
         * 2025-01-20T10:19:14.017+08:00  INFO 11056 --- [           main] o.apache.catalina.core.StandardEngine    :
         *      Starting Servlet engine: [Apache Tomcat/10.1.5]
         *      > 启动 servlet 引擎，包含tomcat版本号
         * 2025-01-20T10:19:14.076+08:00  INFO 11056 --- [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       :
         *      Initializing Spring embedded WebApplicationContext
         *      > 初始化Spring内置的web容器
         * 2025-01-20T10:19:14.077+08:00  INFO 11056 --- [           main] w.s.c.ServletWebServerApplicationContext :
         *      Root WebApplicationContext: initialization completed in 778 ms
         *      > 根容器启动耗时
         * 2025-01-20T10:19:14.365+08:00  INFO 11056 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  :
         *      Tomcat started on port(s): 8080 (http) with context path ''
         *      > 设置虚拟目录的信息
         * 2025-01-20T10:19:14.370+08:00  INFO 11056 --- [           main] c.gardenia.FirstSpringBootApplication    :
         *      Started FirstSpringBootApplication in 1.41 seconds (process running for 1.86)
         *      > JVM初始化耗时
         */
        SpringApplication.run(FirstSpringBootApplication.class, args);  // 运行SpringBoot程序
    }
}
