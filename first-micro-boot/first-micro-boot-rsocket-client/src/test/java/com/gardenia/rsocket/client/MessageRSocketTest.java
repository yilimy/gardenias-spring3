package com.gardenia.rsocket.client;

import com.gardenia.rsocket.utils.Constants;
import com.gardenia.rsocket.vo.Message;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author caimeng
 * @date 2025/5/6 11:00
 */
@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@SpringBootTest(classes = StartRSocketClientApplication.class)
public class MessageRSocketTest {
    @Autowired
    private Mono<RSocketRequester> requesterMono;   // 通过该实例进行服务调用

    /**
     * 有请求，有响应的测试
     */
    @Test
    public void echoMessageTest() { // 测试服务响应
        requesterMono
                .map(requester -> requester
                        // 配置访问路径
                        .route(Constants.URI_ECHO)
                        // 配置请求数据
                        .data(new Message("李兴华", "沐言科技编程讲师"))
                )
                // 对数据进行响应处理
                .flatMap(response -> response
                        .retrieveMono(Message.class)
                        // 进行数据的接收，日志处理
                        .doOnNext(message -> System.out.println("【Echo】" + message))
                        // 进行异常处理
                        .doOnError(Throwable::printStackTrace)
                ).block();
    }

    /**
     * 消息的删除操作
     * <p>
     *     无消息响应的测试
     */
    @Test
    public void deleteMessageTest() {
        requesterMono
                .map(requester -> requester
                        .route(Constants.URI_DELETE)
                        .data("李兴华")
                )
                // 删除操作不需要返回结果，发送成功即可
                .flatMap(RSocketRequester.RetrieveSpec::send)
                .block();
    }

    /**
     * 从服务端读取流式响应
     */
    @Test
    public void listMessageTest() {
        requesterMono
                .map(requester -> requester.route(Constants.URI_LIST))
                // 传递多个数据
                .flatMapMany(response -> response.retrieveFlux(Message.class))  // 调用的类型不应该是Mono，而是Flux
                .doOnNext(message -> System.out.println("【Echo】" + message))
                .doOnError(Throwable::printStackTrace)
                // 在多个数据的最后一个进行等待
                .blockLast();
    }

    /**
     * 客户端与服务端建立交互通道，并接收服务端的流式响应
     */
    @Test
    public void getListMessageTest() {
        //noinspection ReactiveStreamsUnusedPublisher
        Flux<String> titleFlux = Flux.just("muyan", "yootk", "edu");  // 获取消息的标题
        requesterMono
                .map(requester -> requester
                        .route(Constants.URI_GET_LIST)
                        // 要发送的数据
                        .data(titleFlux)
                )
                // 删除操作不需要返回结果，发送成功即可
                .flatMapMany(response -> response.retrieveFlux(Message.class))
                .doOnNext(message -> System.out.println("【Echo】" + message))
                .doOnError(Throwable::printStackTrace)
                .blockLast();
    }
}
