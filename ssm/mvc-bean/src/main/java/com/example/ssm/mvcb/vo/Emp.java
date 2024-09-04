package com.example.ssm.mvcb.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 雇员对象
 * @author caimeng
 * @date 2024/9/3 11:38
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Emp {
    private Long empno;
    private String ename;
    private Date hiredate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "GMT+8")
    private Date updateTime;
    private Dept dept;
}
