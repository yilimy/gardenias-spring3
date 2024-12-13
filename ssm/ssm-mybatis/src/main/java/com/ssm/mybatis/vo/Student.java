package com.ssm.mybatis.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author caimeng
 * @date 2024/12/13 17:23
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
public class Student extends Member implements Serializable {
    /** 学生成绩 **/
    private Double score;
    /** 学生专业 **/
    private String major;
}
