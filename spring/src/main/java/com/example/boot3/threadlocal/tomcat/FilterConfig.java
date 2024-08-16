package com.example.boot3.threadlocal.tomcat;

import jakarta.servlet.Filter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author caimeng
 * @date 2024/8/16 14:51
 */
@Slf4j
@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<Filter> logMDCFilterRegistration() {
        FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();
        // 注入过滤器
//        registration.setFilter(new ClearThreadLocalFilter());
        registration.setFilter(((request, response, chain) -> {
            try {
                chain.doFilter(request, response);
            } finally {
                ThreadLocalController.LOCAL_MULTIPLEX.remove();
            }
        }));
        // 设置拦截规则
        registration.addUrlPatterns("/*");
        // 设置拦截名称
        registration.setName("clearThreadLocalFilter");
        // 设置拦截顺序
        registration.setOrder(99);
        return registration;
    }
}
