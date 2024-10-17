package com.example.ssm.mvcb.config;

import com.example.ssm.mvcb.context.config.SpringWebContextConfig;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.AuthenticatedAuthorizationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
     * <p>
     *     用户信息配置
     *     {@link com.example.ssm.mvcb.service.YootkUserDetailsService#loadUserByUsername(String)}
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
                                .requestMatchers("/pages/message/**")
                                .authenticated()
                                /*
                                 * access(方法) 等同于直接调用 authenticated()
                                 * 只允许认证后的用户访问
                                 */
                                .requestMatchers("/pages/authenticated/**")
                                .access(AuthenticatedAuthorizationManager.authenticated())
                                /*
                                 * 用户详情中配置了角色
                                 * com.example.ssm.mvcb.service.YootkUserDetailsService.loadUserByUsername
                                 * 注意: 角色 ADMIN， 配置的值为 ROLE_ADMIN，"ROLE_"为前缀
                                 */
                                .requestMatchers("/pages/role/info")
                                .hasRole("ADMIN")
                                // 任意角色，命中其一
                                .requestMatchers("/pages/role/any")
                                .hasAnyRole("NEWS", "ADMIN")
                                /*
                                 * 配置一个不存在的路径
                                 * http://localhost/pages/message/info
                                 * 先认证，再跳转到404
                                 */
                                .requestMatchers("/pages/news/**")
                                .hasAnyRole("NEWS")
                                // 任意访问
                                .requestMatchers("/**")
                                .permitAll()
                )
                // Spring Security 内部自带登录表单
                .formLogin();
        // 关闭 CSRF 功能
        http.csrf().disable();
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

    @Bean
    public PasswordEncoder passwordEncoder() {
        // 密码加密器
        return new BCryptPasswordEncoder();
    }

}
