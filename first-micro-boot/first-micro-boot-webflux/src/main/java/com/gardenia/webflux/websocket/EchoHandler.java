package com.gardenia.webflux.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

/**
 * @author caimeng
 * @date 2025/4/29 15:28
 */
@Slf4j
@Component
public class EchoHandler implements WebSocketHandler {
    @SuppressWarnings("NullableProblems")
    @Override
    public Mono<Void> handle(@NonNull WebSocketSession session) {
        log.info("WebSocket客户端握手信息: {}", session.getHandshakeInfo().getUri());
        return session.send(
                session.receive().map(msg ->
                        session.textMessage("【Echo】" + msg.getPayloadAsText())));
    }
}
