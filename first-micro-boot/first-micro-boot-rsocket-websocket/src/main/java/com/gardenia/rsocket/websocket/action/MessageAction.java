package com.gardenia.rsocket.websocket.action;

import com.gardenia.rsocket.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * @author caimeng
 * @date 2025/5/12 16:56
 */
@Slf4j
@Controller
public class MessageAction {

    /**
     * Request-And-Response 的模式
     * @param messageMono 消息
     * @return 响应
     */
    @MessageMapping(Constants.URI_ECHO)
    public Mono<String> echoMessage(Mono<String> messageMono) {
        return messageMono.map(msg -> "【echo】" + msg);
    }

    /**
     * Request-Response-Stream 的模式
     * @param mono 消息
     * @return 响应
     */
    @MessageMapping(Constants.URI_REPEAT)
    public Flux<String> repeat(Mono<String> mono) {
        return mono
                .flatMapMany(msg -> Flux
                        .range(0, 3)
                        .map(i -> "【repeat】 " + i + " " + msg)
                        // 间隔1秒响应
                        .delayElements(Duration.ofSeconds(1))
                );
    }
}
