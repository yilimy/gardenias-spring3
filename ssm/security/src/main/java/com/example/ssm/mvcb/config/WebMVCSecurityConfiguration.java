package com.example.ssm.mvcb.config;

import com.example.ssm.mvcb.context.config.SpringWebContextConfig;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

/**
 * @author caimeng
 * @date 2024/9/29 14:55
 */
@Configuration
@EnableWebSecurity  // 启用 Spring Security 的支持
public class WebMVCSecurityConfiguration {  // WEB配置类

    @Bean(name = "mvcHandlerMappingIntrospector")
    public HandlerMappingIntrospector mvcHandlerMappingIntrospector() {
        return new HandlerMappingIntrospector();
    }

    /**
     * 注册 Spring Security 拦截链
     * <p>
     *     注意视图资源配置 {@link ResourceViewConfig#resourceViewResolver()}
     * <p>
     *     因为 Spring Security 版本不一致，导致 SecurityFilterChain 的注入有区别.
     *     当前注入还需要额外注入 HandlerMappingIntrospector 对象
     *     <a href="https://www.bilibili.com/video/BV1BStJeEE6g/" />
     *     本地版本: 6.0.1
     *     参考版本: 6.0.0-M3
     *     {@link HttpSecurity#createMvcMatchers(String...)}
     * @param http HttpSecurity
     * @return security 认证链
     */
    @SneakyThrows
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) {
        // 配置认证的访问请求
        http
                .authorizeHttpRequests((authorizeHttpRequests) ->
                        authorizeHttpRequests
                                // 配置认证目录
                                .requestMatchers("/pages/message/**").authenticated()
                                // 任意访问
                                .requestMatchers("/**").permitAll()
                )
                // Spring Security 内部自带登录表单
                .formLogin();
        return http.build();
    }

    /**
     * 自定义配置，一般针对图片等资源目录
     * 注意映射配置 {@link SpringWebContextConfig#addResourceHandlers(ResourceHandlerRegistry)}
     * @return 自定义配置
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers(
                "/yootk-images/**",
                "/yootk-js/**",
                "/yootk-css/**"
        );
    }

}
