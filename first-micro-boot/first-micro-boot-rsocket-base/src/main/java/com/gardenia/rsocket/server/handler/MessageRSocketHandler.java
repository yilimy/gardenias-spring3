package com.gardenia.rsocket.server.handler;

import io.rsocket.Payload;
import io.rsocket.RSocket;
import io.rsocket.util.DefaultPayload;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author caimeng
 * @date 2025/4/30 14:40
 */
@SuppressWarnings("NullableProblems")
@Slf4j
public class MessageRSocketHandler implements RSocket {


    @Override
    public Mono<Void> fireAndForget(Payload payload) {  // 这是一个没有任何响应的操作
        /*
         * Payload 表示所有的附加信息。
         * 对于 RSocket 通讯来讲，所有的数据项都通过此格式传输。
         */
        String message = payload.getDataUtf8();    // 获取数据
        log.info("【fireAndForget】接收请求数据: {}", message);
        /*
         * 返回一个空消息
         * 一般这种无响应操作，可以应用于日志记录的模式上。
         * 客户发送一个日志，是不需要响应的。
         */
        return Mono.empty();
    }

    @Override
    public Mono<Payload> requestResponse(Payload payload) { // 传统模式，有请求也有响应
        String message = payload.getDataUtf8();    // 获取数据
        log.info("【requestResponse】接收请求数据: {}", message);
        // 进行数据的响应处理
        return Mono.just(DefaultPayload.create("【Echo】" + message));
    }

    @Override
    public Flux<Payload> requestStream(Payload payload) {   // 处理流数据
        String message = payload.getDataUtf8();    // 获取数据
        log.info("【requestStream】接收请求数据: {}", message);
        return Flux.fromStream(message.chars()
                // 获取数据中的每一个字符
                .mapToObj(i -> (char) i)
                // 字符转大写
                .map(Character::toUpperCase)
                // 大写字符转字符串
                .map(String::valueOf)
                // 创建Payload的附加数据
                .map(DefaultPayload::create)
        );
    }

    @Override
    public Flux<Payload> requestChannel(Publisher<Payload> payloads) {  // 双向流
        /*
         * 有发布者，就会有订阅者。
         */
        return Flux.from(payloads)
                .map(Payload::getDataUtf8)
                .map(msg -> {
                    log.info("【requestChannel】接收请求数据: {}", msg);
                    return DefaultPayload.create("【Echo】" + msg);
                });
    }
}
