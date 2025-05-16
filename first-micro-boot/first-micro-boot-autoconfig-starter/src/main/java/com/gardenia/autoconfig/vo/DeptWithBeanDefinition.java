package com.gardenia.autoconfig.vo;

import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.type.AnnotationMetadata;

import static lombok.AccessLevel.PRIVATE;

/**
 * 使用 BeanDefinition 进行注入
 * {@link com.gardenia.autoconfig.regist.DefaultImportBeanDefinitionRegistrar#registerBeanDefinitions(AnnotationMetadata, BeanDefinitionRegistry)}
 * @author caimeng
 * @date 2025/5/15 14:03
 */
@Data
@FieldDefaults(level = PRIVATE)     // 将所有的属性的默认访问权限设置为 private
// 该配置类使用 Selector 进行注入，不用管IDE的报错
@SuppressWarnings("ConfigurationProperties")
@ConfigurationProperties(prefix = "gardenia.dept4")
public class DeptWithBeanDefinition {
    Long deptno;
    String dname;
    String loc = "Beijing";
}
