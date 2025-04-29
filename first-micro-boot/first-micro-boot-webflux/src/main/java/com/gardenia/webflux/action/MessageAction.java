package com.gardenia.webflux.action;

import com.gardenia.webflux.handler.MessageObjHandler;
import com.gardenia.webflux.vo.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author caimeng
 * @date 2025/4/29 11:46
 */
@Slf4j
@RestController
@RequestMapping("/message/*")
public class MessageAction extends AbstractBaseAction {
    @Autowired
    private MessageObjHandler messageObjHandler;

    /**
     * webflux编程
     * <p>
     *     返回单个对象使用 Mono
     * @param message 请求消息
     * @return 返回消息
     */
    @RequestMapping("echo")
    public Mono<?> echo(Message message) {
        log.info("接收用户发送的访问信息，message={}", message);
        /*
         * {
         *   "title": "【reactor-http-nio-2】沐言科技",
         *   "pubdate": "2020-06-30T16:00:00.000+00:00",
         *   "content": "【reactor-http-nio-2】www.yootk.com"
         * }
         */
        return messageObjHandler.echoHandler(message)
                .doOnError(e -> log.error("处理消息时发生异常", e))
                .onErrorResume(e -> buildErrorResponse(message, e));
    }

    /**
     * 返回集合
     * @param message 请求消息
     * @return 返回消息
     */
    @RequestMapping("list")
    public Flux<?> list(Message message) {
        log.info("【list】接收用户发送的访问信息，message={}", message);
        return messageObjHandler.list(message);
    }

    /**
     * 返回集合
     * @param message 请求消息
     * @return 返回消息
     */
    @RequestMapping("map")
    public Flux<?> map(Message message) {
        log.info("【map】接收用户发送的访问信息，message={}", message);
        return messageObjHandler.map(message);
    }

    // 构建错误响应
    private Mono<Message> buildErrorResponse(Message message, Throwable e) {
        // 根据实际需求返回合适的错误响应
        message.setContent(e.getMessage());
        return Mono.just(message);
    }
}
