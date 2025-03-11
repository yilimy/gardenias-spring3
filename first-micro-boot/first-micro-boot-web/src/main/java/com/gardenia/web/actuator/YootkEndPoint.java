package com.gardenia.web.actuator;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * 自定义 EndPoint
 * @author caimeng
 * @date 2025/3/11 15:22
 */
@Endpoint(id = "muyan-endpoint")     // 定义接口名称
@Configuration
public class YootkEndPoint {

    /**
     * 自定义endpoint 方法
     * <a href="http://localhost:9090/actuator/muyan-endpoint/lee" />
     * 注意，因为该方法含有参数，所以不能直接用 <a href="http://localhost:9090/actuator/muyan-endpoint" />
     * @param select 参数
     * @return 返回结果
     */
    @ReadOperation
    public Map<String, Object> endpoint(@Selector String select) {  // 获取一些参数数据
        return Map.of(
                "name", "Yootk",
                "author", "李兴华",
                "select", select,
                "version", "1.0.0"
        );
    }
}
