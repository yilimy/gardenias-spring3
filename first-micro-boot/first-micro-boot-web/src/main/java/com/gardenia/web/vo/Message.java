package com.gardenia.web.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Date;

/**
 * @author caimeng
 * @date 2025/1/22 12:00
 */
@Data
public class Message {
    private String title;
    @JSONField(format = "yyyy年MM月dd日")  // fastjson 组件的转换, 但是要求请求参数也为这个format类型
    private Date pubDate;
    private String content;
}
