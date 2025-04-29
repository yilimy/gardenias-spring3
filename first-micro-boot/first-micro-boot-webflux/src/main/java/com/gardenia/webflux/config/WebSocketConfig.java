package com.gardenia.webflux.config;

import com.gardenia.webflux.websocket.EchoHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author caimeng
 * @date 2025/4/29 15:34
 */
@Configuration
public class WebSocketConfig {  // 配置WebSocket

    @Bean
    public HandlerMapping webSocketMapping(@Autowired EchoHandler echoHandler) {
        // 定义所有的映射集合
        Map<String, EchoHandler> map = new HashMap<>();
        // 配置WebSocket映射路由
        map.put("/websocket/{token}", echoHandler);
        SimpleUrlHandlerMapping  mapping = new SimpleUrlHandlerMapping();
        mapping.setOrder(Ordered.HIGHEST_PRECEDENCE);
        mapping.setUrlMap(map);
        return mapping;
    }

    @Bean
    public WebSocketHandlerAdapter webSocketHandlerAdapter() {
        return new WebSocketHandlerAdapter();
    }
}
