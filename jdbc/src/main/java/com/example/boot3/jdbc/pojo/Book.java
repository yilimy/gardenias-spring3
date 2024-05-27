package com.example.boot3.jdbc.pojo;

import lombok.Data;

/**
 * java 关联结构对象
 * <p>
 *     1. 类名称 = 表名称
 *     2. 类成员属性 = 表字段
 *     3. 类实例化对象 = 单行表数据
 *     4. 对象数组 = 多行数据
 * @author caimeng
 * @date 2024/5/27 10:29
 */
@Data
public class Book {
    /**
     * 对应主键
     */
    private Long bid;
    /**
     * 图书名
     */
    private String title;
    /**
     * 作者
     */
    private String author;
    /**
     * 价格
     */
    private Double price;
}
