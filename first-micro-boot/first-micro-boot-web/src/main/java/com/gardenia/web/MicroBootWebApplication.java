package com.gardenia.web;   // 父包，这个包中所有子包的类会被自动扫描

import com.gardenia.web.banner.GardeniaBanner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;

/**
 * @author caimeng
 * @date 2025/1/20 11:25
 */
@ImportResource(locations = "classpath:META-INF/spring/spring-*.xml")
@SpringBootApplication  // 一个注解解决所有的问题
@ServletComponentScan   // Servlet 组件扫描
public class MicroBootWebApplication {
//    public static void main(String[] args) {
//        SpringApplication.run(StartSpringBootApplication.class, args);
//    }

    /**
     * 含有banner的启动入口
     * <p>
     *     配置文件中需没有指定banner，使启动程序走 banner 的 fallback 分支
     *     {@link SpringApplication#printBanner(ConfigurableEnvironment)}
     *     {@link org.springframework.boot.SpringApplicationBannerPrinter#getBanner(Environment)}
     * @param args 启动方法
     */
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(MicroBootWebApplication.class);
        application.setBanner(new GardeniaBanner());
        // 关闭 Banner 的输出
//        application.setBannerMode(Banner.Mode.OFF);
        application.run(args);
    }
}
