package com.gardenia.autoconfig.config;

import com.gardenia.autoconfig.vo.Dept;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 自动装配类
 * @author caimeng
 * @date 2025/5/15 15:49
 */
@Configuration  // Bean注入
@EnableConfigurationProperties(Dept.class)    // 希望帮助找到配置的属性
public class YootkConfiguration {
}
