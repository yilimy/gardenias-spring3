package com.example.boot3.valid.pojo;

import com.example.boot3.valid.config.SkipValidate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * @author caimeng
 * @date 2024/5/17 17:21
 */
@SkipValidate({"age"})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserValid {
    @NotBlank(message = "用户名[userName]不能为空")
    private String userName;
    @NotNull(message = "用户年龄[age]不能为空")
    private Integer age;
}
