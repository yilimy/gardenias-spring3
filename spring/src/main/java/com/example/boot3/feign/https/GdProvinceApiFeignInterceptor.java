package com.example.boot3.feign.https;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

/**
 * 印章中心拦截器 <br>
 * <p> 复制请求头 <br>
 * 不能注入spring容器（@Configuration），否则变成全局配置
 * @author caimeng
 * @date 2023/11/7 18:32
 */
@SuppressWarnings("unused")
@Slf4j
//@Configuration
public class GdProvinceApiFeignInterceptor implements RequestInterceptor {
    /**
     * 复制请求头时的排除项
     */
    private static final List<String> HEADER_EXCLUDE = Arrays.asList(
            "appkey", "secret", "random", "digest", "content-length");
    @Override
    public void apply(RequestTemplate template) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        /*
         * hystrix开启后，默认使用线程池多线程代理feign请求，此时将丢失主线程，导致获取不到上下文
         * 因此，一旦开启了hystrix，需要将策略改为SEMAPHORE
         * hystrix.command.default.execution.isolation.strategy=SEMAPHORE
         * 如果不想通过主线程信号量的方式，则需要重写Hystrix线程池策略 @Configuration(extends HystrixConcurrencyStrategy)
         *
         * 大致操作：抄一下Hystrix中的实现类 SleuthHystrixConcurrencyStrategy，改一下wrapCallable(Callable<T>)方法
         * wrapCallable方法中返回的对象为，自定义 WrappedCallable 保存请求上下文 attributes
         */
        if (null != attributes) {
            HttpServletRequest request = attributes.getRequest();
            Enumeration<String> headerNames = request.getHeaderNames();
            if (headerNames != null) {
                while (headerNames.hasMoreElements()) {
                    String name = headerNames.nextElement();
                    if (HEADER_EXCLUDE.contains(name)) {
                        continue;
                    }
                    String values = request.getHeader(name);
                    template.header(name, values);
                }
            }
            extraHeaderSetter(template, request);
        }
    }

    /**
     * 设置额外的请求头 <br>
     * <a href="http://192.168.200.143:5080/guomaixinan/ElectronicSealManagement/secureApply/business">用章管理系统</a>
     * 安全应用 - 业务系统管理
     * @param template 请求上下文
     * @param request 请求体
     */
    private static void extraHeaderSetter(RequestTemplate template, HttpServletRequest request) {
        // 设置appkey
        ServletContext servletContext = request.getServletContext();
        WebApplicationContext requiredWebApplicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
//        GdForwardConfig config = requiredWebApplicationContext.getBean(GdForwardConfig.class);
//        String appkey = config.getAppkey();
//        String secret = config.getSecret();
//        if (!ObjectUtils.isEmpty(appkey)
//                && !ObjectUtils.isEmpty(secret)) {
//            template.header("appkey", appkey);
//            String random = RandomUtil.randomNumbers(6);
//            template.header("random", random);
//            String plain = appkey + secret + random;
//            String digest = SM3.create().digestHex(plain);
//            template.header("digest", digest);
//            log.info("appkey={}, random={}, secret={}, digest={}", appkey, random, secret, digest);
//        }
    }
}
