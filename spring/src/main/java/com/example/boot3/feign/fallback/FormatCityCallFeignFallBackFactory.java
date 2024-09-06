package com.example.boot3.feign.fallback;

import com.example.boot3.feign.FormatCityCallFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author caimeng
 * @date 2024/9/5 18:15
 */
@Slf4j
@Component
public class FormatCityCallFeignFallBackFactory implements FallbackFactory<FormatCityCallFeign> {
    @Override
    public FormatCityCallFeign create(Throwable cause) {
        return (uri, header, signParam) -> {
                log.error("调用地市进行摘要签章失败,uri={}, header={}, signParam={}", uri, header, signParam);
                log.error("调用地市进行摘要签章失败", cause);
                return null;
            };
    }
}
