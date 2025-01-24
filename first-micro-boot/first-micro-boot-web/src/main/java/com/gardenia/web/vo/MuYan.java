package com.gardenia.web.vo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author caimeng
 * @date 2025/1/24 18:10
 */
@Data
@Component
@ConfigurationProperties(prefix = "muyan")
public class MuYan {
    private String mysql;   // 属性的名称是key的名称
    private String redis;
    private List<String> messages;
    private Map<String, String> books;
}
