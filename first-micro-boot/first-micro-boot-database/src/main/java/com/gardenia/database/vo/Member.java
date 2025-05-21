package com.gardenia.database.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author caimeng
 * @date 2025/5/21 16:31
 */
@Data
public class Member {
    private String mid;
    private String name;
    private Integer age;
    private Double salary;
    private Date birthday;
    private String content;
    private Integer del;
}
