package com.gardenia.webflux.router;

import com.gardenia.webflux.handler.MessageHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * 路由器
 * @author caimeng
 * @date 2025/4/27 10:18
 */
@Configuration
public class MessageRouter {

    @Bean   // 路由的功能注册
    public RouterFunction<ServerResponse> routerEcho(MessageHandler messageHandler) {
        return RouterFunctions.route(
                // 路由规则
                RequestPredicates
                        // 映射访问路径
                        .GET("/echo")
                        // 请求头信息
                        .and(RequestPredicates.accept(MediaType.TEXT_PLAIN)),
                // 处理器
                messageHandler::echoHandler);
    }
}
