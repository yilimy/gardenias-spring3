package com.example.mybatis.po;

import lombok.Data;

/**
 * @author caimeng
 * @date 2024/5/10 17:12
 */
@Data
public class User {
    /**
     * id
     */
    private Integer id;
    /**
     * 用户名称
     */
    private String name;
    /**
     * 年龄
     */
    private Integer age;
}
