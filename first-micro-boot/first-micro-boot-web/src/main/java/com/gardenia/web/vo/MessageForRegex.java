package com.gardenia.web.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.gardenia.web.common.validation.annotation.RegexValidator;
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
public class MessageForRegex {
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
    @Digits(integer = 1, fraction = 0, message = "{message.level.digits.error}")
    private Integer level;
    // 追加字段，例如：yootk-103
    @RegexValidator(regex = "^[a-zA-Z]{1,5}-\\d{1,3}$", message = "{message.flag.regex.error}")
    private String flag;
}
