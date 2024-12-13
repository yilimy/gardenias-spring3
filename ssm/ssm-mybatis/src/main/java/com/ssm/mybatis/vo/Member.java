package com.ssm.mybatis.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author caimeng
 * @date 2024/12/13 17:21
 */
@Data
public class Member implements Serializable {
    /** 用户ID **/
    private String mid;
    /** 用户姓名 **/
    private String name;
    /** 用户年龄 **/
    private Integer age;
    /** 用户性别 **/
    private String sex;
}
