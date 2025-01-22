package com.gardenia.web.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 缺陷: 某些基于反射模式来获取setter方法，都是获取的void返回值的方法签名，如果setter有返回值，该框架的兼容性会收到影响。
 * 该访问器模式已经能兼容大部分的框架了。
 * @author caimeng
 * @date 2025/1/22 15:11
 */
@Data
/*
 * 访问器模式 chain
 * 1. 拥有 getter 和 setter 方法，能与一般的开发框架兼容
 * 2. setter 方法改 void 为返回对象本身，支持链式调用
 */
@Accessors(chain = true)
public class Person {
    private String name;
    private Integer age;
}
