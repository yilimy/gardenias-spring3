package com.gardenia.web.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author caimeng
 * @date 2025/1/22 15:54
 */
@Data
/*
 * 访问器模式，混和模式
 * 1. fluent + prefix
 *      prefix生效了，由 thName 生成了 name() 的getter方法，以及 name(String) 的setter方法
 * 2. chain + prefix
 *      同上，prefix生效了，属性只剩下 name 和 grade
 */
@Accessors(chain = true, prefix = "th")
public class Teacher {
    private String thName;
    private Integer age;
    // 年级
    private String thGrade;
}
