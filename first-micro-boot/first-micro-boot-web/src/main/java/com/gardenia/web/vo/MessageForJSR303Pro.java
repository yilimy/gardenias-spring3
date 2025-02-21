package com.gardenia.web.vo;

import com.alibaba.fastjson.annotation.JSONField;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

/**
 * 通过配置文件的方式，提示错误信息
 * @author caimeng
 * @date 2025/2/21 16:45
 */
@Data
public class MessageForJSR303Pro {
    @NotBlank(message = "{message.title.not.blank.error}")
    private String title;
    @NotNull(message = "{message.pubDate.not.null.error}")
    @JSONField(format = "yyyy年MM月dd日")
    private Date pubDate;
    @NotBlank(message = "{message.content.not.blank.error}")
    private String content;
    @Email(message = "{message.email.email.error}")
    @NotNull(message = "{message.email.not.null.error}")
    private String email;
    // 整数部分不超过1位，小数部分不超过0位
    @Digits(integer = 1, fraction = 0, message = "{message.level.digits.error}")
    private Integer level;
}
