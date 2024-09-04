package com.example.ssm.mvcb.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 部门对象
 * @author caimeng
 * @date 2024/9/3 11:37
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Dept {
    private Long deptno;
    private String dname;
    private String loc;
}
