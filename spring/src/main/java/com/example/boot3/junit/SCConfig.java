package com.example.boot3.junit;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 四川企业侧配置文件
 * @author caimeng
 * @date 2023/9/19 17:39
 */
@Component
@Data
@PropertySource(value = "classpath:sc.properties", ignoreResourceNotFound = true)
@ConfigurationProperties(prefix = "sc")
public class SCConfig {
    public static final String TYPE_PROVINCE = "province";
    public static final String TYPE_CITY = "city";
    /**
     * 服务所在地类型：省province or 市州city
     */
    private String location;
    /**
     * 前置服务访问省枢纽或市州的地址
     * location=province 省枢纽
     * location=city 市州
     */
    private String host;
    /**
     * 认证的appkey
     */
    private String appKey;
    /**
     * 认证的secret
     */
    private String appSecret;
    /**
     * 如果nginx没有开启真实IP，需要打开此配置
     */
    private Boolean ipEnable;
    /**
     * 缓存本地ip
     */
    private Boolean ipCache;
}
