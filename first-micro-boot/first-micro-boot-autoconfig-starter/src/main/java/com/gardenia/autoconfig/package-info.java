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
 * <p>
 *     使自动配置类生效，指 factories 文件，这个文件是不同模块间进行自动装配的主要文件。
 *     文件路径：src/main/resources/META-INF/spring.factories。
 *     必须要 EnableAutoConfiguration 注解标记才能生效 (在@SpringBootApplication就有，自定义上下文启动的时候要注意)。
 *     注意：Spring Boot 3.x 之后不再支持 Spring Boot 2.x 的自动装配，换成以下配置文件
 *          META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports
 * @author caimeng
 * @date 2025/5/15 17:48
 */
package com.gardenia.autoconfig;