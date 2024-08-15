package com.example.amqp.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 部门实体类
 * @author caimeng
 * @date 2024/8/15 10:31
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Dept implements Serializable {
    private Long deptno;
    private String dname;
    private String loc;
}
