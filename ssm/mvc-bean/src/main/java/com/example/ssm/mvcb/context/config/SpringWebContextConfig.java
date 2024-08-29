package com.example.ssm.mvcb.context.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.util.UrlPathHelper;

/**
 * @author caimeng
 * @date 2024/8/29 11:17
 */
@EnableWebMvc   // 加入此注解才表示WebMVC配置生效
@Configuration
@ComponentScan("com.example.ssm.mvcb.action")
public class SpringWebContextConfig implements WebMvcConfigurer {

    // 矩阵参数配置
    @Override
    public void configurePathMatch(@NonNull PathMatchConfigurer configurer) {
        var urlPathHelper = new UrlPathHelper();
        // 启用矩阵参数接收
        urlPathHelper.setRemoveSemicolonContent(false);
        configurer.setUrlPathHelper(urlPathHelper);
    }
}
