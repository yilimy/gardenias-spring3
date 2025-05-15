package com.gardenia.autoconfig.vo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 使用 Selector 进行注入
 * {@link com.gardenia.autoconfig.selector.DefaultImportSelector#selectImports(AnnotationMetadata)}
 * @author caimeng
 * @date 2025/5/15 14:03
 */
@Data
// 该配置类使用 Selector 进行注入，不用管IDE的报错
@SuppressWarnings("ConfigurationProperties")
@ConfigurationProperties(prefix = "gardenia.dept3")
public class DeptWithSelector {
    private Long deptno;
    private String dname;
    private String loc;
}
