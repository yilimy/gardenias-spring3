package com.example.boot3.feign.https;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author caimeng
 * @date 2023/12/28 20:42
 */
@Slf4j
@Component
public class GdProvinceApiFeignHystrixFactory implements FallbackFactory<GdProvinceApiFeign> {

    @Override
    public GdProvinceApiFeign create(Throwable cause) {
        return new GdProvinceApiFeign() {};
    }
}
