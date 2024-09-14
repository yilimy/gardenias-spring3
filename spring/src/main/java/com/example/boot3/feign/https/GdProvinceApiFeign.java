package com.example.boot3.feign.https;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * 访问广东省印章系统的feign
 * @author caimeng
 * @date 2023/12/28 20:37
 */
@FeignClient(
        contextId = "context-gd-api",
        name = "gd-province-api",
        url = "${forward.gd.host:}",
        path = "${forward.gd.path:}",
        fallbackFactory = GdProvinceApiFeignHystrixFactory.class,
        configuration = {GdProvinceApiFeignInterceptor.class, GdProvinceApiFeignSSLConfig.class}
)
public interface GdProvinceApiFeign {

}
