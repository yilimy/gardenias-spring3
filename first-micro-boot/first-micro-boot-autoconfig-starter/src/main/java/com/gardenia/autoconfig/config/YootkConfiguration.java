package com.gardenia.autoconfig.config;

import com.gardenia.autoconfig.vo.Dept;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 自动装配类
 * <p>
 *     使用  EnableConfigurationProperties 的注入
 * @author caimeng
 * @date 2025/5/15 15:49
 */
@Configuration  // Bean注入
// Dept 的 bean名称定义：前缀 + 横杠 + 类全名
@EnableConfigurationProperties(Dept.class)    // 希望帮助找到配置的属性
public class YootkConfiguration {
}
