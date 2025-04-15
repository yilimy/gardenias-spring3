package com.gardenia.web.config;

import com.gardenia.web.interceptor.DefaultHandlerInterceptor;
import com.gardenia.web.interceptor.MDCInterceptor;
import com.gardenia.web.interceptor.MessageValidatorInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author caimeng
 * @date 2025/2/8 16:20
 */
@Configuration
@ConditionalOnProperty(value = "interceptor.enable", havingValue = "true")
public class WebInterceptorConfigure implements WebMvcConfigurer {
    @Override
    public void addInterceptors(@NonNull InterceptorRegistry registry) {    // 追加拦截器
        registry.addInterceptor(defaultHandlerInterceptor())
                // 配置拦截的路径
                .addPathPatterns("/**");
        registry.addInterceptor(messageValidatorInterceptor())
                .addPathPatterns("/validate/echo");
        registry.addInterceptor(mdcInterceptor())
                .addPathPatterns("/**");
    }

    @Bean
    public DefaultHandlerInterceptor defaultHandlerInterceptor() {
        return new DefaultHandlerInterceptor();
    }

    @Bean
    public MessageValidatorInterceptor messageValidatorInterceptor() {
        return new MessageValidatorInterceptor();
    }

    @Bean
    public MDCInterceptor mdcInterceptor() {
        return new MDCInterceptor();
    }
}
