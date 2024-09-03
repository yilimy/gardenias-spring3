package com.example.ssm.mvcb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.util.List;

/**
 * @author caimeng
 * @date 2024/9/3 16:51
 */
@Configuration
public class JacksonConfig {    // JSON配置类

    @Bean
    public RequestMappingHandlerAdapter requestMappingHandlerAdapter() {
        RequestMappingHandlerAdapter adapter = new RequestMappingHandlerAdapter();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        // MIME类型
        converter.setSupportedMediaTypes(List.of(MediaType.APPLICATION_JSON));
        adapter.setMessageConverters(List.of(converter));
        return adapter;
    }
}
