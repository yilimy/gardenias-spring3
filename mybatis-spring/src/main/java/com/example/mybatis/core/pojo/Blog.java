package com.example.mybatis.core.pojo;

import lombok.Data;

/**
 * @author caimeng
 * @date 2024/5/29 14:44
 */
@Data
public class Blog {
    private Integer id;
    private String userName;
    private String title;
    private String context;
}
