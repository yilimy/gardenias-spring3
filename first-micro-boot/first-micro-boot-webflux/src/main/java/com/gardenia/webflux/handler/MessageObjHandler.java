package com.gardenia.webflux.handler;

import com.gardenia.webflux.vo.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
    /**
     * 返回单个数据对象的案例
     * @param message 数据对象
     * @return 结果对象
     */
    public Mono<Message> echoHandler(Message message) { // 直接以最终的返回数据类型进行操作
        log.info("【{}】业务层接收处理数据: {}", Thread.currentThread().getName(), message);
        message.setTitle("【" + Thread.currentThread().getName() + "】" + message.getTitle());
        message.setContent("【" + Thread.currentThread().getName() + "】" + message.getContent());
        return Mono.create(sink -> sink.success(message));
    }

    /**
     * 实现集合的响应【list】
     * @param message 数据对象
     * @return 结果list对象
     */
    public Flux<Message> list(Message message) {
        log.info("【{}】业务层接收处理数据: {}", Thread.currentThread().getName(), message);
        List<Message> messages = IntStream.range(1, 10).mapToObj(i -> {
            Message m = new Message();
            m.setTitle("【" + i + "】" + message.getTitle());
            m.setContent("【" + i + "】" + message.getContent());
            m.setPubdate(message.getPubdate());
            return m;
        }).toList();
        return Flux.fromIterable(messages);
    }

    /**
     * 实现集合的响应【Map】
     * <p>
     *     每次响应的时候，都只能配置具体的类型
     * @param message 数据对象
     * @return 结果list对象
     */
    public Flux<Map.Entry<String, Message>> map(Message message) {
        log.info("【{}】业务层接收处理数据: {}", Thread.currentThread().getName(), message);
        Map<String, Message> retMap = IntStream.range(1, 10)
                // 因为 IntStream中缺失对 Collectors.toMap 的支持，因此将其作为包装流，使用Stream的toMap方法
                .boxed()
                .collect(
                Collectors.toMap(
                        i -> "yootk - " + i,
                        i -> {
                            Message m = new Message();
                            m.setTitle("【" + i + "】" + message.getTitle());
                            m.setContent("【" + i + "】" + message.getContent());
                            m.setPubdate(message.getPubdate());
                            return m;
                        }));
        return Flux.fromIterable(retMap.entrySet());
    }
}
