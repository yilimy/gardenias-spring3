package com.gardenia.autoconfig.config;

import com.gardenia.autoconfig.vo.DeptWithImport;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 使用 Import 进行注入
 * <p>
 *     1. Import 注入的类，会自动注入到容器中，因此不需要使用 @Bean 注入
 *     2. 如果注入的 Bean 有很多，可以使用 selector 的方式进行注入
 *          {@link com.gardenia.autoconfig.selector.DefaultImportSelector}
 *          {@link YootkConfigurationWithImportSelector}
 *     3. 以上是 Spring 容器对 Bean 的自动注册，如果想自定义Bean的方式注入，使用 ImportBeanDefinitionRegistrar
 *          {@link com.gardenia.autoconfig.regist.DefaultImportBeanDefinitionRegistrar}
 *          {@link YootkConfigurationWithBeanDefinition}
 * @author caimeng
 * @date 2025/5/15 16:50
 */
@Configuration
// 因为是 Import 导入的，因此不会有 bean 的名称：gardenia.dept2-com.gardenia.autoconfig.vo.DeptWithImport
// 如要使用 bean 的名称定义，使用  EnableConfigurationProperties 进行导入
@Import(DeptWithImport.class)
public class YootkConfigurationWithImport {
}
