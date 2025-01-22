package com.gardenia.web.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author caimeng
 * @date 2025/1/22 12:00
 */
@Data
public class Message {
    private String title;
    private Date pubDate;
    private String content;
}
