package com.gardenia.rsocket.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author caimeng
 * @date 2025/4/30 19:12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private String title;
    private String content;
}
