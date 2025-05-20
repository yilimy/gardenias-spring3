package com.gardenia.database.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 参考 {@link org.springframework.boot.autoconfigure.jdbc.DataSourceProperties}
 * @author caimeng
 * @date 2025/5/20 14:13
 */
@Data
@ConfigurationProperties("spring.test-qqq.datasource")
public class QqqDatasourceProperties {
    private Boolean enabled;
    private String name;
    private String driverClassName;
    private String url;
    private String username;
    private String password;
}
