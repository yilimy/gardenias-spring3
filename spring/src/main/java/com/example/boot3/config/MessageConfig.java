package com.example.boot3.config;

import com.example.boot3.bean.MessageChannel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

/**
 * @author caimeng
 * @date 2024/4/11 11:25
 */
@Configuration
public class MessageConfig {

    @Bean
    public MessageChannel genMessageChannel() {
        return MessageChannel.builder()
                .host("127.0.0.1")
                .token(UUID.randomUUID().toString())
                .build();
    }
}
