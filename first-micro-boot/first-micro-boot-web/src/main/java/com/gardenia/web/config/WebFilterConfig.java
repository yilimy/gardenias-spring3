package com.gardenia.web.config;

import com.gardenia.web.filter.EduFilter;
import com.gardenia.web.filter.MuYanFilter;
import com.gardenia.web.filter.YootkFilter;
import jakarta.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 测试：过滤器的执行顺序
 * @author caimeng
 * @date 2025/2/7 18:28
 */
@Configuration
public class WebFilterConfig {

    @Bean
    public Filter getMuYanFilter() {
        return new MuYanFilter();
    }

    @Bean
    public Filter getEduFilter() {
        return new EduFilter();
    }

    @Bean
    public Filter getYootkFilter() {
        return new YootkFilter();
    }

    @Bean
    public FilterRegistrationBean<?> getMuYanFilterRegistrationBean() {
        FilterRegistrationBean<Filter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
        filterFilterRegistrationBean.setFilter(getMuYanFilter());
        filterFilterRegistrationBean.setName("MFilter");
        filterFilterRegistrationBean.addUrlPatterns("/orders/*");
        // 设置过滤器的执行顺序
        filterFilterRegistrationBean.setOrder(5);
        return filterFilterRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean<?> getEduFilterRegistrationBean() {
        FilterRegistrationBean<Filter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
        filterFilterRegistrationBean.setFilter(getEduFilter());
        filterFilterRegistrationBean.setName("AFilter");
        filterFilterRegistrationBean.addUrlPatterns("/orders/*");
        filterFilterRegistrationBean.setOrder(2);
        return filterFilterRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean<?> getYootkFilterRegistrationBean() {
        FilterRegistrationBean<Filter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
        filterFilterRegistrationBean.setFilter(getYootkFilter());
        filterFilterRegistrationBean.setName("ZFilter");
        filterFilterRegistrationBean.addUrlPatterns("/orders/*");
        filterFilterRegistrationBean.setOrder(-100);
        return filterFilterRegistrationBean;
    }

}
