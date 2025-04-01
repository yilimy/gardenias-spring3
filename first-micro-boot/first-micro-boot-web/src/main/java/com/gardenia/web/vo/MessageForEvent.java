package com.gardenia.web.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 事件监听实体类
 * @author caimeng
 * @date 2025/2/21 16:45
 */
@NoArgsConstructor
@Data
public class MessageForEvent {
    private String title;
    private String url;

    public MessageForEvent(String title, String url) {
        this.title = title;
        this.url = url;
    }
}
