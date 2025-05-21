package com.gardenia.database.config;

import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.support.jakarta.StatViewServlet;
import com.alibaba.druid.support.jakarta.WebStatFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.List;

/**
 * 监控界面的配置类
 * @author caimeng
 * @date 2025/5/20 16:15
 */
@Configuration
public class DruidMonitorConfig {
    public static final String DRUID_STAT_VIEW_SERVLET_NAME = "druidStatViewServlet";
    public static final String WEB_STAT_FILTER_NAME = "webStatFilter";
    public static final String SQL_STAT_FILTER_NAME = "sqlStatFilter";

    /**
     * 通过浏览器进行访问
     * <a href="http://localhost/druid"></a>
     * @return 注册 Servlet
     */
    @Bean(DRUID_STAT_VIEW_SERVLET_NAME)
    public ServletRegistrationBean<StatViewServlet> druidStatViewServlet(){
        ServletRegistrationBean<StatViewServlet> bean = new ServletRegistrationBean<>(
                new StatViewServlet(), "/druid/*");
        // 设置登录用户名和密码
        bean.addInitParameter(StatViewServlet.PARAM_NAME_USERNAME, "admin");
        bean.addInitParameter(StatViewServlet.PARAM_NAME_PASSWORD, "123456");
        // IP地址白名单
        bean.addInitParameter(StatViewServlet.PARAM_NAME_ALLOW, "127.0.0.1");
        // 黑名单
        bean.addInitParameter(StatViewServlet.PARAM_NAME_DENY, "127.0.0.2");
        // 允许重置
        bean.addInitParameter(StatViewServlet.PARAM_NAME_RESET_ENABLE, "true");
        return bean;
    }

    @Bean(WEB_STAT_FILTER_NAME)
    public WebStatFilter webStatFilter(){
        WebStatFilter webStatFilter = new WebStatFilter();
        // 对session状态进行监控
        webStatFilter.setSessionStatEnable(true);
        return webStatFilter;
    }

    @Bean
    @DependsOn(WEB_STAT_FILTER_NAME) // 依赖名称为 webStatFilter 的Bean
    public FilterRegistrationBean<WebStatFilter> webStatFilterRegistrationBean(WebStatFilter webStatFilter){
        FilterRegistrationBean<WebStatFilter> bean = new FilterRegistrationBean<>(webStatFilter);
        // 对所有的路径都进行监控配置
        bean.setUrlPatterns(List.of("/*"));
        // 排除一些资源路径的访问监控，以及 /druid 路径的监控
        bean.addInitParameter(WebStatFilter.PARAM_NAME_EXCLUSIONS,
                "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*");
        return bean;
    }

    /**
     * @param mergeSql 统计相同的SQL命令
     * @param logSlowSql 记录慢SQL
     * @param showSqlMillis 慢SQL执行的标准
     * @return SQL监控的拦截器
     */
    @Bean(SQL_STAT_FILTER_NAME)
    public StatFilter sqlStatFilter(@Value("${spring.datasource.druid.stat.merge-sql:false}") boolean mergeSql,
                                    @Value("${spring.datasource.druid.stat.log-slow-sql:false}") boolean logSlowSql,
                                    @Value("${spring.datasource.druid.stat.show-sql-millis:1000}") long showSqlMillis){
        StatFilter statFilter = new StatFilter();
        // 慢查询的阈值，单位是毫秒
        statFilter.setSlowSqlMillis(showSqlMillis);
        // 是否开启慢查询日志
        statFilter.setLogSlowSql(logSlowSql);
        // 是否开启合并SQL
        statFilter.setMergeSql(mergeSql);
        return statFilter;
    }
}
