/**
 * <p>
 *     注解 Import 的特性
 *       1. 以组件或配置类的方式注入
 *          {@link com.gardenia.autoconfig.config.YootkConfigurationWithImport}
 *       2. 以 Selector 的方式注入
 *          {@link com.gardenia.autoconfig.config.YootkConfigurationWithImportSelector}
 *       3. 以 BeanDefinition 的方式注入
 *          {@link com.gardenia.autoconfig.config.YootkConfigurationWithBeanDefinition}
 * <p>
 *     实现配置信息的提示
 *     1. 添加配置提示依赖，在依赖和 maven-compiler-plugin 中
 *          org.springframework.boot:spring-boot-configuration-processor
 *     2. 执行项目编译, build:compile
 *     3. 查看编译后文件，这个文件就是自动装配的提示性文件，地址:
 *          classes/META-INF/spring-autoconfigure-metadata.properties
 *     当以上文件生成后，在编辑yml文件时，会进行提示。
 * @author caimeng
 * @date 2025/5/15 17:48
 */
package com.gardenia.autoconfig;