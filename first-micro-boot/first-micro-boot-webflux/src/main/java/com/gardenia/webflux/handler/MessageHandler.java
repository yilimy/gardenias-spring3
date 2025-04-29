package com.gardenia.webflux.handler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * WebFlux的处理模块
 * <p>
 *     在webflux中进行响应，分为单个响应和多个响应
 * @author caimeng
 * @date 2025/4/27 10:06
 */
@Component
public class MessageHandler {
    /**
     * 请求接收和响应
     * @param request 请求体
     * @return 单一的数据返回
     */
    public Mono<ServerResponse> echoHandler(ServerRequest request) {
        return ServerResponse.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML_VALUE)
                .body(BodyInserters.fromValue("沐言科技，www.yootk.com"));
    }
}
