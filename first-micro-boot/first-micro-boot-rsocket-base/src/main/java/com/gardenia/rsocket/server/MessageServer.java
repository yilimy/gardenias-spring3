package com.gardenia.rsocket.server;

import com.gardenia.rsocket.server.acceptor.MessageRSocketAcceptor;
import io.rsocket.core.RSocketServer;
import io.rsocket.frame.decoder.PayloadDecoder;
import io.rsocket.transport.netty.server.TcpServerTransport;
import reactor.core.Disposable;

/**
 * 服务端启动器
 * @author caimeng
 * @date 2025/4/30 17:25
 */
public class MessageServer {
    private static Disposable disposable;   // 用于释放任务

    public static void start() {
        RSocketServer rSocketServer = RSocketServer.create(new MessageRSocketAcceptor());
        // 采用零拷贝技术，提升IO性能
        rSocketServer.payloadDecoder(PayloadDecoder.ZERO_COPY);
        // 开启端口订阅
        disposable = rSocketServer.bind(TcpServerTransport.create(6565)).subscribe();
    }

    public static void stop() {
        // 释放任务
        disposable.dispose();
    }
}
