package com.gardenia.autoconfig.vo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>
 *     Bean类上有ConfigurationProperties注解，表名该定义为一个组件，需要用Component进行标记
 *     这里是通过 EnableConfigurationProperties 进行注入
 * @author caimeng
 * @date 2025/5/15 14:03
 */
@Data
@ConfigurationProperties(prefix = "gardenia.dept")
public class Dept {
    private Long deptno;
    private String dname;
    private String loc;
}
