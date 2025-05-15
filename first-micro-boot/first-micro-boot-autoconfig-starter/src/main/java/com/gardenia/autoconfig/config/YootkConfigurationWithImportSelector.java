package com.gardenia.autoconfig.config;

import com.gardenia.autoconfig.selector.DefaultImportSelector;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 使用 Import 和 Selector 进行注入
 * @author caimeng
 * @date 2025/5/15 16:50
 */
@Configuration
@Import(DefaultImportSelector.class)
public class YootkConfigurationWithImportSelector {
}
