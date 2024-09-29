package com.example.ssm.mvcb.context.config;

import com.example.ssm.mvcb.config.JacksonConfig;
import com.example.ssm.mvcb.web.config.StartWebAnnotationApplication;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.util.UrlPathHelper;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 使用 {@link StartWebAnnotationApplication} 应该开启 EnableWebMvc 注解
 * @author caimeng
 * @date 2024/8/29 11:17
 */
@EnableWebMvc   // 加入此注解才表示WebMVC配置生效,但是本地添加此配置后，使用StartWebApplication会报错：找不到ServletContext
@Configuration
@ComponentScan("com.example.ssm.mvcb.action")
public class SpringWebContextConfig implements WebMvcConfigurer {

    // 矩阵参数配置
    @Override
    public void configurePathMatch(@NonNull PathMatchConfigurer configurer) {
        var urlPathHelper = new UrlPathHelper();
        // 启用矩阵参数接收
        urlPathHelper.setRemoveSemicolonContent(false);
        configurer.setUrlPathHelper(urlPathHelper);
    }

    /**
     * 定义消息转换器
     * <p>
     *     该功能与其他地方的配置重合
     *     {@link JacksonConfig#requestMappingHandlerAdapter()}
     * @param converters initially an empty list of converters
     */
    @Override
    public void configureMessageConverters(@NonNull List<HttpMessageConverter<?>> converters) {
        WebMvcConfigurer.super.configureMessageConverters(converters);
        // 自定义的全局消息转换器，重复功能，去掉
//        definedConfigureMessageConverters(converters);
    }

    /*
     * 视频中这个配置是生效的，但是我的是不生效，反而是本地的配置没有问题
     * <a href="https://www.bilibili.com/video/BV173H5eEEeE/" />
     * @see com.example.ssm.mvcb.config.JacksonConfig#requestMappingHandlerAdapter()
     * 使用该配置，响应头是text，而不是json
     * @param converters initially an empty list of converters
     */
    @SuppressWarnings("unused")
    public void definedConfigureMessageConverters(@NonNull List<HttpMessageConverter<?>> converters) {
        // 可以配置所有的消息转换器
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        objectMapper.setDateFormat(new SimpleDateFormat(JacksonConfig.DATE_TIME_FORMAT));
        converter.setSupportedMediaTypes(List.of(MediaType.APPLICATION_JSON));
        converter.setObjectMapper(objectMapper);
        converters.add(converter);
    }

    @Override
    public void addResourceHandlers(@NonNull ResourceHandlerRegistry registry) {
        // yootk-js路径映射到 /WEB-INF/static/js/
        registry.addResourceHandler("yootk-js/**").addResourceLocations("/WEB-INF/static/js/");
        registry.addResourceHandler("yootk-css/**").addResourceLocations("/WEB-INF/static/css/");
        registry.addResourceHandler("yootk-img/**").addResourceLocations("/WEB-INF/static/images/");
    }
}
