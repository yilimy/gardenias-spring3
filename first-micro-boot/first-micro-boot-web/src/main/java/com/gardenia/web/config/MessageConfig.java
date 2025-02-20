package com.gardenia.web.config;

import com.gardenia.web.vo.Message;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author caimeng
 * @date 2025/2/20 15:15
 */
@Configuration
public class MessageConfig {
    @Bean
    public Message getMessage() {
        Message message = new Message();
        message.setTitle("Spring Boot");
        message.setPubDate(new java.util.Date());
        message.setContent("Spring Boot 2.0");
        return message;
    }
}
