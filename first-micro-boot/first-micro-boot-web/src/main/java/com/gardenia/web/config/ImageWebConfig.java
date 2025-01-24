package com.gardenia.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author caimeng
 * @date 2025/1/24 10:28
 */
@Configuration
public class ImageWebConfig implements WebMvcConfigurer {

    @Override
    public void extendMessageConverters(@NonNull List<HttpMessageConverter<?>> converters) {
        // 追加扩充的转换器
        // 追加图像转换操作
        converters.add(new BufferedImageHttpMessageConverter());
    }
}
