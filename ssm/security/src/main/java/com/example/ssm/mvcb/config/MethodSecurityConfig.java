package com.example.ssm.mvcb.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

/**
 * 方法安全控制
 * @author caimeng
 * @date 2024/10/14 18:31
 */
@Configuration
// 启用业务层方法的认证和授权检测. @PreAuthorize 和 @PostAuthorize等的启用
@EnableMethodSecurity(
        // @Secured 注解启用 (可选)
        securedEnabled = true,
        // jsr-250安全注解标准 (可选)
        jsr250Enabled = true
)
public class MethodSecurityConfig {
}
