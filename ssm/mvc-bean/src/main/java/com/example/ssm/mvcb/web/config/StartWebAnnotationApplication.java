package com.example.ssm.mvcb.web.config;

import com.example.ssm.mvcb.context.config.SpringApplicationContextConfig;
import com.example.ssm.mvcb.context.config.SpringWebContextConfig;
import jakarta.servlet.Filter;
import jakarta.servlet.MultipartConfigElement;
import jakarta.servlet.ServletRegistration;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * 启动类
 * 另一种实现 {@link StartWebApplication}
 * @author caimeng
 * @date 2024/9/18 11:12
 */
public class StartWebAnnotationApplication extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{SpringApplicationContextConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{SpringWebContextConfig.class};
    }

    @NonNull
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    protected Filter[] getServletFilters() {
        CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
        encodingFilter.setEncoding("UTF-8");
        encodingFilter.setForceEncoding(true);
        return new Filter[]{encodingFilter};
    }

    /**
     * 配置的注册处理
     * @param registration the {@code DispatcherServlet} registration to be customized
     */
    @Override
    protected void customizeRegistration(@NonNull ServletRegistration.Dynamic registration) {
        /*
         * 单个文件上传的最大长度
         * 限制大小为 2M
         */
        long maxFileSize = 1024 * 1024 * 2;
        /*
         * 最大请求长度 5M
         * 总的上传大小限制
         */
        long maxRequestSize = 1024 * 1024 * 5;
        /*
         * 达到阈值写入磁盘
         * 直接写入
         */
        int fileSizeThreshold = 0;
        // 文件路径地址：C:\Users\EDY\.SmartTomcat\SpringBoot3Demo\ssm\mvc-bean\work\Catalina\localhost\ROOT\tmp
        MultipartConfigElement element = new MultipartConfigElement(
                "/tmp", maxFileSize, maxRequestSize, fileSizeThreshold);
        // 配置注册
        registration.setMultipartConfig(element);
    }
}
