package com.gardenia.web.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * 使用fastjson替换掉jackson
 * @author caimeng
 * @date 2025/1/23 11:23
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void configureMessageConverters(@NonNull List<HttpMessageConverter<?>> converters) {
        // 消息转换器
        // 1. spring默认的转换器为jackson，首先要将其移除
        for (int i = converters.size() - 1 ; i >= 0; i--) {
            Object item = converters.get(i);
            if (item instanceof MappingJackson2HttpMessageConverter) {
                converters.remove(i);
            }
        }
        // 2. 创建fastjson的转换器
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
        // 3. 配置fastjson的配置
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(
                SerializerFeature.WriteMapNullValue,    // 允许Map的内容为空
                SerializerFeature.WriteNullListAsEmpty,     // 如果list的集合为空，使用中括号代替
                SerializerFeature.WriteNullStringAsEmpty,       // 如果String内容为空，使用空字符串代替
                SerializerFeature.WriteDateUseDateFormat,       // 日期格式化输出
                SerializerFeature.WriteNullNumberAsZero,        // 数字为空，置为0
                SerializerFeature.DisableCircularReferenceDetect    // 禁用循环引用
        );
        fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
        // 4. 最终输出的内容是Json，所以要配置响应的头信息结构
        List<MediaType> fastMediaTypeList = new ArrayList<>();  // 定义所有响应的类型
        fastMediaTypeList.add(MediaType.APPLICATION_JSON);  // 使用json类型进行响应
        fastJsonHttpMessageConverter.setSupportedMediaTypes(fastMediaTypeList);
        // 5. 在转换器列表中添加当前配置的fastjson的组件
        converters.add(fastJsonHttpMessageConverter);
    }
}
