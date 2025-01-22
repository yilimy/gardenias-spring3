package com.gardenia.web.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 该访问器适配的环境:
 *      一些ORM框架在表设计的时候，会在字段前添加一些前缀，但是在程序处理的时候又需要优化掉这些前缀。
 * @author caimeng
 * @date 2025/1/22 15:11
 */
@Data
/*
 * 访问器模式 prefix
 * 1. 自动生成的getter和setter方法中，会按照去掉prefix的方式处理
 * 2. 没有该前缀的属性，将不会生成getter和setter方法
 * 3. 没有链式调用
 * <pre>
 *  public String getName() {
 *      return this.stuName;
 *  }
 *  public void setName(String stuName) {
 *      this.stuName = stuName;
 *  }
 * </pre>
 */
@Accessors(prefix = "stu")
public class Student {
    private String stuName;
    private Integer age;
    // 学分
    private Double stuCredit;
    /*
     * 用来测试大小写。
     * 并没有生成属性为"dent"的getter和setter方法，说明 prefix 要求属性后一位必需是大写
     */
    private String student;
}
