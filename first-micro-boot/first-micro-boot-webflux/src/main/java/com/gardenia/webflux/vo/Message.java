package com.gardenia.webflux.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author caimeng
 * @date 2025/4/29 11:17
 */
@Data
public class Message {
    private String title;
    private Date pubdate;
    private String content;
}
