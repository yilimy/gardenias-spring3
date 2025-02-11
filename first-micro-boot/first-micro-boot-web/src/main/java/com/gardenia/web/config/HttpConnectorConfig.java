package com.gardenia.web.config;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Http连接器配置
 * <p>
 *      1. 打开了80端口
 *      2. 如果用户访问的是http://localhost:8080/，则会自动跳转到http://localhost:80/
 *      3. tomcat启动后，会出现两个端口同时监听的日志
 *              Tomcat initialized with port(s): 443 (https) 80 (http)
 * @author caimeng
 * @date 2025/2/11 15:03
 */
@Configuration
// 当server.port=443时，才会生效
@ConditionalOnProperty(value = "server.port", havingValue = "443")
public class HttpConnectorConfig {
    @Bean
    public Connector getHttpConnector() {
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setScheme("http");    // http协议访问
        connector.setPort(80);      // 监听端口为80
        connector.setSecure(false);     // 不使用安全连接
        connector.setRedirectPort(443);     // 强制重定向到https端口
        return connector;
    }

    // 整合到tomcat中
    @Bean
    // 当classpath中存在TomcatServletWebServerFactory时，才会生效
    @ConditionalOnClass(name = "org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory")
    public TomcatServletWebServerFactory tomcatServletWebServerFactory() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
            @Override
            protected void postProcessContext(Context context) {    // 发送处理上下文
                super.postProcessContext(context);
                // 想办法把所有匹配的操作跳转到https
                SecurityConstraint securityConstraint = new SecurityConstraint();
                // 设置为约束
                securityConstraint.setUserConstraint("CONFIDENTIAL");
                SecurityCollection collection = new SecurityCollection();
                /*
                 * 所有的路径项都进行匹配。
                 * tomcat 中使用的是 *, Spring 中使用的是 **
                 */
                collection.addPattern("/*");
                securityConstraint.addCollection(collection);
                context.addConstraint(securityConstraint);
            }
        };
        // 将设置好的连接器添加到tomcat中
        tomcat.addAdditionalTomcatConnectors(getHttpConnector());
        return tomcat;
    }
}
