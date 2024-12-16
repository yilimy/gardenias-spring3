package com.ssm.mybatis.vo;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author caimeng
 * @date 2024/12/16 16:35
 */
@Data
public class CoursePO implements Serializable {
    /**
     * 课程编号
     */
    private Long cid;
    /**
     * 课程名称
     */
    private String cname;
    /**
     * 学分
     */
    private Integer credit;
    /**
     * 学生列表
     * 课程对象也描述了学生的业务逻辑
     */
    @ToString.Exclude
    private List<StudentPO> students;
}
