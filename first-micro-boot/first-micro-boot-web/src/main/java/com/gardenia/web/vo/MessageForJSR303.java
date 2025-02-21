package com.gardenia.web.vo;

import com.alibaba.fastjson.annotation.JSONField;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

/**
 * @author caimeng
 * @date 2025/2/21 16:45
 */
@Data
public class MessageForJSR303 {
    @NotBlank
    private String title;
    @NotNull
    @JSONField(format = "yyyy年MM月dd日")
    private Date pubDate;
    @NotBlank
    private String content;
    @Email
    @NotNull
    private String email;
    @Digits(integer = 1, fraction = 0)  // 整数部分不超过1位，小数部分不超过0位
    private Integer level;
}
