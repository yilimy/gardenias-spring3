package com.gardenia.database.config;

import com.alibaba.druid.support.jakarta.StatViewServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 监控界面的配置类
 * @author caimeng
 * @date 2025/5/20 16:15
 */
@Configuration
public class DruidMonitorConfig {

    /**
     * 通过浏览器进行访问
     * <a href="http://localhost/druid"></a>
     * @return 注册 Servlet
     */
    @Bean("druidStatViewServlet")
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
}
