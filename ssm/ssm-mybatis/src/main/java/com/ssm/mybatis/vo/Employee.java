package com.ssm.mybatis.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author caimeng
 * @date 2024/12/13 17:27
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
public class Employee extends Member implements Serializable {
    /** 雇员收入 **/
    private Double salary;
    /** 所属部门 **/
    private String dept;
}
