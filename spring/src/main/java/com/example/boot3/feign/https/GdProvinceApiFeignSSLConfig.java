package com.example.boot3.feign.https;

import com.example.boot3.https.rest.HttpsClientRequestFactory;
import feign.Client;
import org.springframework.context.annotation.Bean;

import javax.net.ssl.SSLContext;

/**
 * 支持 https 的实例
 * <p>
 *     该类中如果使用了 @Configuration 注解，那么它是全局配置。
 *     如果没有使用 @Configuration 注解，而由 @FeignClient(configuration) 指定，则是指定feign的客户端使用的配置。
 * @author caimeng
 * @date 2024/9/13 18:50
 */
public class GdProvinceApiFeignSSLConfig {
    @Bean
    public Client feignClient() throws Exception {
        SSLContext sc = HttpsClientRequestFactory.createIgnoreVerifySSL();
        return new Client.Default(sc.getSocketFactory(), (args1, args2) -> true);
    }
}
