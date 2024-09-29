package com.example.ssm.mvcb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * 资源视图的映射配置
 * @author caimeng
 * @date 2024/9/20 11:02
 */
@Configuration
public class ResourceViewConfig {

    @Bean
    public InternalResourceViewResolver resourceViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        // 统一的前缀配置
        resolver.setPrefix("/WEB-INF/pages");
        // 统一的后缀配置
        resolver.setSuffix(".jsp");
        return resolver;
    }
}
