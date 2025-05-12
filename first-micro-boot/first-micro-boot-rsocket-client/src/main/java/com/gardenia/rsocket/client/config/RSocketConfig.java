package com.gardenia.rsocket.client.config;

import io.rsocket.transport.netty.client.TcpClientTransport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.codec.cbor.Jackson2CborDecoder;
import org.springframework.http.codec.cbor.Jackson2CborEncoder;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

/**
 * 要与 RSocket-server 进行通信，就要设置编解码规则
 * @author caimeng
 * @date 2025/5/6 10:10
 */
@Configuration
public class RSocketConfig {
    @Bean
    public RSocketStrategies rSocketStrategies() {
        return RSocketStrategies.builder()
                /*
                 * RSocket使用的是 CBOR 通讯协议。
                 * CBOR（Concise Binary Object Representation）是一种用于数据序列化的二进制格式，
                 * 主要用于高效的数据交换，尤其适用于物联网（IoT）和嵌入式系统等对数据传输量和解析效率要求较高的场景
                 */
                .encoders(encodes -> encodes.add(new Jackson2CborEncoder()))
                .decoders(decoders -> decoders.add(new Jackson2CborDecoder()))
                .build();
    }

    @Bean
    public Mono<RSocketRequester> rSocketRequester(RSocketRequester.Builder builder) {
        return Mono.just(builder.rsocketConnector(connector -> connector
                // 重试两次，重试时间间隔2秒
                .reconnect(Retry.fixedDelay(2, Duration.ofSeconds(2))))
                // 设置传输类型
                .dataMimeType(MediaType.APPLICATION_CBOR)
                // 设置连接端口
                .transport(TcpClientTransport.create(6869))
        );
    }
}
