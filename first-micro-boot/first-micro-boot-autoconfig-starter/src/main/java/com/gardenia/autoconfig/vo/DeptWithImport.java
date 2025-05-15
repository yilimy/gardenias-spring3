package com.gardenia.autoconfig.vo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author caimeng
 * @date 2025/5/15 14:03
 */
@Data
@ConfigurationProperties(prefix = "gardenia.dept2")
public class DeptWithImport {
    private Long deptno;
    private String dname;
    private String loc;
}
