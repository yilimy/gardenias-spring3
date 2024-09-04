package com.example.ssm.mvcb.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

/**
 * @author caimeng
 * @date 2024/9/3 16:51
 */
@Configuration
public class JacksonConfig {    // JSON配置类
    public final static String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @Bean
    public RequestMappingHandlerAdapter requestMappingHandlerAdapter() {
        RequestMappingHandlerAdapter adapter = new RequestMappingHandlerAdapter();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = new ObjectMapper();
        // 忽略json数据中不识别的属性
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 忽略空对象
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // 格式化输出
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        // 空值不参与序列化
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        // 设置时区：东八区
        objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        // 日期类型的字符串处理
        objectMapper.setDateFormat(new SimpleDateFormat(DATE_TIME_FORMAT));
        // 设置jackson的自定义配置
        converter.setObjectMapper(objectMapper);
        // MIME类型
        converter.setSupportedMediaTypes(List.of(MediaType.APPLICATION_JSON));
        adapter.setMessageConverters(List.of(converter));
        return adapter;
    }
}
