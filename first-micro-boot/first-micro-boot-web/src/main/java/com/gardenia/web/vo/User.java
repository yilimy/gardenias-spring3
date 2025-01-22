package com.gardenia.web.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 缺陷：在一些开发框架时，比如MyBatis、SpringDataJPA、Jackson，要求通过setter方法进行设置，通过getter方法进行值获取。
 * 但是对于独立的类而言，fluent还是很强大的。
 * @author caimeng
 * @date 2025/1/22 15:11
 */
@Data
/*
 * 访问器模式 fluent
 * 1. 不会自动生成getter和setter方法，如果存在Getter和Setter注解，则该两项注解无效
 * 2. 无参的属性方法是getter : name()
 * 3. 有参的属性方法是setter : User name(String name)
 * 4. 自带链式
 */
@Accessors(fluent = true)
public class User {
    private String name;
    private Integer age;
}
