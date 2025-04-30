package com.gardenia.rsocket.server.action;

import com.gardenia.rsocket.server.service.MessageService;
import com.gardenia.rsocket.vo.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * 控制器
 * <p>
 *     注意：
 *      1. 不要使用 @RestController 注解，否则会报错
 *      2. 所有的控制层方法，必须通过“@MessageMapping”注解配置才能对外提供服务
 * @author caimeng
 * @date 2025/4/30 19:40
 */
@Slf4j
@Controller
public class MessageAction {
    @Autowired
    private MessageService messageService;

    /**
     * 该方法接收请求和进行响应
     * @param messageMono 请求对象
     * @return 响应
     */
    @MessageMapping("message.echo")
    public Mono<Message> echoMessage(Mono<Message> messageMono) {
        log.info("echoMessage");
        return messageMono
                // 响应的处理
                .doOnNext(message -> messageService.echo(message))
                // 日志处理
                .doOnNext(message -> log.info("【消息接收】: {}", message));
    }

    /**
     * 该方法不需要响应
     * 根据title进行删除
     * @param titleMono 待删除条件
     */
    @MessageMapping("message.delete")
    public void deleteMessage(Mono<String> titleMono) {
        log.info("deleteMessage");
        titleMono
                .doOnNext(title -> log.info("【消息删除】: {}", title))
                .subscribe();
    }

    /**
     * list集合发送
     * @return Flux集合
     */
    @MessageMapping("message.list")
    public Flux<Message> listMessage() {
        log.info("listMessage");
        return Flux.fromStream(messageService.list().stream());
    }

    /**
     * list集合发送
     * @return Flux集合
     */
    @MessageMapping("message.getList")
    public Flux<Message> getListMessage(Flux<String> titleFlux) {
        log.info("getListMessage");
        return titleFlux
                .doOnNext(title -> log.info("【消息接收】: {}", title))
                .map(String::toLowerCase)
                .map(messageService::get)
                // 数据延缓输出
                .delayElements(Duration.ofSeconds(1));
    }
}
