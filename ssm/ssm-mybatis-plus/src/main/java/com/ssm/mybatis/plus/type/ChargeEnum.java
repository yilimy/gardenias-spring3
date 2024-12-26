package com.ssm.mybatis.plus.type;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

import java.util.Arrays;

/**
 * 负责人的枚举类
 * @author caimeng
 * @date 2024/12/25 10:51
 */
public enum ChargeEnum {
    Lee("李兴华", "讲师"),
    Wang("小王", "隔壁的"),
    LittleLee("小李老师", "小师傅");
    @Getter
    @EnumValue  // 枚举值
    private final String name;    // 枚举的核心项
    @Getter
    private final String desc;

    ChargeEnum(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public static ChargeEnum of(String name) {
        return Arrays.stream(values()).filter(v -> v.getName().equals(name)).findAny().orElse(null);
    }
}
