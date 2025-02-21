package com.gardenia.web.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Date;

/**
 * @author caimeng
 * @date 2025/1/22 12:00
 */
@Data
public class MessageForValidate {
    private String title;
    @JSONField(format = "yyyy年MM月dd日")
    private Date pubDate;
    private String content;
}
