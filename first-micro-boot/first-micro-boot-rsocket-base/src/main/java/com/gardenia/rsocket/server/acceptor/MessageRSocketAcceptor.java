package com.gardenia.rsocket.server.acceptor;

import com.gardenia.rsocket.server.handler.MessageRSocketHandler;
import io.rsocket.ConnectionSetupPayload;
import io.rsocket.RSocket;
import io.rsocket.SocketAcceptor;
import reactor.core.publisher.Mono;

/**
 * 连接器
 * @author caimeng
 * @date 2025/4/30 17:22
 */
public class MessageRSocketAcceptor implements SocketAcceptor {
    @SuppressWarnings("NullableProblems")
    @Override
    public Mono<RSocket> accept(ConnectionSetupPayload setup, RSocket sendingSocket) {
        // 配置一个自己的处理类
        return Mono.just(new MessageRSocketHandler());
    }
}
