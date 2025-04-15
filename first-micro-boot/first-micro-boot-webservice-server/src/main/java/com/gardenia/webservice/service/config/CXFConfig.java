package com.gardenia.webservice.service.config;

import com.gardenia.webservice.service.impl.MessageServiceImpl;
import com.gardenia.webservice.service.interceptor.WebServiceAuthInterceptor;
import javax.xml.ws.Endpoint;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author caimeng
 * @date 2025/4/9 14:31
 */
@Configuration
public class CXFConfig {
    @Autowired
    private Bus bus;    // 注入BUS实例
    @Autowired
    private MessageServiceImpl messageService;  // 注入业务实例
    @Autowired
    private WebServiceAuthInterceptor interceptor;    // 注入拦截器

    @Bean
    public ServletRegistrationBean<CXFServlet> servletRegistrationBean() {
        return new ServletRegistrationBean<>(new CXFServlet(), "/services/*");
    }

    @Bean
    public Endpoint messageEndpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, messageService);
        endpoint.getInInterceptors().add(interceptor);
        return endpoint;
    }
}
