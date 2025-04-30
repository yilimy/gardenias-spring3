package com.gardenia.rsocket.server;

import com.gardenia.rsocket.server.handler.MessageRSocketHandler;
import io.rsocket.Payload;
import io.rsocket.RSocket;
import io.rsocket.core.RSocketConnector;
import io.rsocket.transport.netty.client.TcpClientTransport;
import io.rsocket.util.DefaultPayload;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;

import java.time.Duration;

/**
 * @author caimeng
 * @date 2025/4/30 17:37
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)   // 手工配置方法的执行顺序
public class MessageServerTest {
    private static RSocket client;

    @BeforeAll
    public static void stepUpClient() {
        // 服务启动
        MessageServer.start();
        client = RSocketConnector
                // 创建一个连接端口6565的客户端连接
                .connectWith(TcpClientTransport.create(6565))
                // 使用阻塞的模式连接
                .block();
    }

    /**
     * 测试RSocket的无响应模式
     * {@link MessageRSocketHandler#fireAndForget(Payload)}
     */
    @Test
    public void fireAndForgetTest() {
        sendRequestPayload()
                .flatMap(client::fireAndForget)
                // 等待所有数据发送完成后，添加一点延时，可以等待时间长一些
                .blockLast(Duration.ofMinutes(1));
    }

    /**
     * 测试RSocket的请求响应模式
     * {@link MessageRSocketHandler#requestResponse(Payload)}
     */
    @Test
    public void requestResponseTest() {
        sendRequestPayload()
                .flatMap(client::requestResponse)
                // 请求响应模式的特点是，每次响应后，有数据返回
                .doOnNext(response -> System.out.println("【requestResponse】接收响应数据: " + response.getDataUtf8()))
                .blockLast(Duration.ofMinutes(1));
    }

    /**
     * 测试RSocket的流响应模式
     * {@link MessageRSocketHandler#requestStream(Payload)}
     */
    @Test
    public void requestStreamTest() {
        sendRequestPayload()
                .flatMap(client::requestStream)
                .doOnNext(response -> System.out.println("【requestStream】接收响应数据: " + response.getDataUtf8()))
                .blockLast(Duration.ofMinutes(1));
    }

    /**
     * 测试RSocket的交互模式
     * {@link MessageRSocketHandler#requestChannel(Publisher)}
     */
    @Test
    public void requestChannelTest() {
        /*
         * Channel 需要有一个订阅者和发布者.
         * 申请一个通道。
         */
        client.requestChannel(sendRequestPayload())
                .doOnNext(response -> System.out.println("【requestChannel】接收响应数据: " + response.getDataUtf8()))
                .blockLast(Duration.ofMinutes(1));

    }

    /**
     * 模拟数据发送
     * @return 数据集合
     */
    private static Flux<Payload> sendRequestPayload() {  // 传递所有的附加数据内容
        return Flux.just("yootk.com", "springboot", "springcloud", "redis", "netty", "elasticsearch")
                // 创建数据延时，每秒创建一个
                .delayElements(Duration.ofSeconds(1))
                .map(DefaultPayload::create);
    }

    @AfterAll
    public static void stepDownServer() {
        // 关闭客户端
        client.dispose();
        // 服务关闭
        MessageServer.stop();
    }
}
