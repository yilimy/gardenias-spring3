package com.gardenia.web.config;

import com.gardenia.web.interceptor.DefaultHandlerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author caimeng
 * @date 2025/2/8 16:20
 */
@Configuration
public class WebInterceptorConfigure implements WebMvcConfigurer {
    @Override
    public void addInterceptors(@NonNull InterceptorRegistry registry) {    // 追加拦截器
        registry.addInterceptor(defaultHandlerInterceptor())
                // 配置拦截的路径
                .addPathPatterns("/**");
    }

    @Bean
    public HandlerInterceptor defaultHandlerInterceptor() {
        return new DefaultHandlerInterceptor();
    }
}
