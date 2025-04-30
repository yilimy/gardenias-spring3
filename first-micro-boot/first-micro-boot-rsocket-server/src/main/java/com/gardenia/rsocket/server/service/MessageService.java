package com.gardenia.rsocket.server.service;

import com.gardenia.rsocket.vo.Message;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author caimeng
 * @date 2025/4/30 19:15
 */
@Service
public class MessageService {

    public List<Message> list() {   // 响应集合数据
        return List.of(
                new Message("yootk", "沐言优拓，www.yootk.com"),
                new Message("muyan", "沐言科技，www.yootk.com"),
                new Message("edu", "李兴华课程"),
                new Message("title4", "content4"),
                new Message("title5", "content5"),
                new Message("title6", "content6"),
                new Message("title7", "content7"),
                new Message("title8", "content8"),
                new Message("title9", "content9"),
               new Message("title10", "content10")
        );
    }

    public Message get(String title) {  // 响应单个数据
        return new Message(title, "【" + title + "】www.yootk.com");
    }

    @SuppressWarnings("UnusedReturnValue")
    public Message echo(Message message) {  // 响应单个数据
        message.setTitle("【ECHO】" + message.getTitle());
        message.setContent("【ECHO】" + message.getContent());
        return message;
    }
}
