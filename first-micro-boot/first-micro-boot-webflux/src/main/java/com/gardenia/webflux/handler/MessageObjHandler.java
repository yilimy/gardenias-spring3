package com.gardenia.webflux.handler;

import com.gardenia.webflux.vo.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

/**
 * 返回对象的Handler
 * <p>
 *     此时不再关注
 *     {@link org.springframework.web.reactive.function.server.ServerRequest}
 *     {@link org.springframework.web.reactive.function.server.ServerResponse}
 *     这两个类型了
 *     相比较: {@link MessageHandler#echoHandler(ServerRequest)}
 * @author caimeng
 * @date 2025/4/29 11:19
 */
@Slf4j
@Component
public class MessageObjHandler {
    public Mono<Message> echoHandler(Message message) { // 直接以最终的返回数据类型进行操作
        log.info("【{}】业务层接收处理数据: {}", Thread.currentThread().getName(), message);
        message.setTitle("【" + Thread.currentThread().getName() + "】" + message.getTitle());
        message.setContent("【" + Thread.currentThread().getName() + "】" + message.getContent());
        return Mono.create(sink -> sink.success(message));
    }
}
