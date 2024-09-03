package com.example.ssm.mvcb.vo;

import lombok.Data;

import java.util.Date;

/**
 * 雇员对象
 * @author caimeng
 * @date 2024/9/3 11:38
 */
@Data
public class Emp {
    private Long empno;
    private String ename;
    private Date hiredate;
    private Dept dept;
}
