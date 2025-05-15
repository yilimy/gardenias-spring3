package com.gardenia.autoconfig.config;

import com.gardenia.autoconfig.regist.DefaultImportBeanDefinitionRegistrar;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 使用 Import 和 BeanDefinition 进行注入
 * @author caimeng
 * @date 2025/5/15 16:50
 */
@Configuration
@Import(DefaultImportBeanDefinitionRegistrar.class)
public class YootkConfigurationWithBeanDefinition {
}
