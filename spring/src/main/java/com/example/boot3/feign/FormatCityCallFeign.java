package com.example.boot3.feign;

import com.example.boot3.feign.fallback.FormatCityCallFeignFallBackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.Map;

/**
 * 地市远程调用
 * <p>
 *     1. 该接口中的所有方法都必须手动指定URI
 *     2. 该接口已禁用负载均衡
 * @author caimeng
 * @date 2024/9/5 15:48
 */
@FeignClient(
        value = "FORMAT-API-SHENZHEN",
        // 指定一个欺骗性的url，用来禁用掉负载均衡，也可以使用某个真实的默认url
        url = "http://fakeLoadBalancerUrl",
        fallbackFactory = FormatCityCallFeignFallBackFactory.class
)
public interface FormatCityCallFeign {

    /**
     * 调用地市进行摘要签章
     * <p>
     *     使用 RequestMapping 注解时，只能配套使用 http 的注解 (变量 isHttpAnnotation)
     *          {@link feign.Contract.BaseContract#parseAndValidateMetadata(Class, Method)}
     *          {@link feign.Contract.Default#processAnnotationsOnParameter(feign.MethodMetadata, Annotation[], int)}
     *     而要使用feign的 {@link feign.HeaderMap} 注解，必须把 RequestMapping 换成 {@link feign.RequestLine} ，并使用 RequestLine 的规则
     * @param signParam 签章参数
     * @return 签章结果
     */
    @RequestMapping(value = "/gdPrefix/v2/core/digestStamp", method = RequestMethod.POST)
    Object digestStamp(URI uri, @RequestHeader Map<String, String> header, @RequestBody Object signParam);
}
