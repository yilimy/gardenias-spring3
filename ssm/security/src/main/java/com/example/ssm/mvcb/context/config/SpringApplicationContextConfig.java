package com.example.ssm.mvcb.context.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 服务端配置
 * <p>
 *     如果要使用
 *     {@link com.example.ssm.mvcb.web.config.StartWebAnnotationApplication}
 *     配置的话，要把 "com.example.ssm.mvcb.config" 加入扫描目录
 * @author caimeng
 * @date 2024/8/29 11:17
 */
@Configuration
@ComponentScan({
        "com.example.ssm.mvcb.service",
        "com.example.ssm.mvcb.config",
        "com.example.ssm.mvcb.advice"
})
public class SpringApplicationContextConfig {
}
