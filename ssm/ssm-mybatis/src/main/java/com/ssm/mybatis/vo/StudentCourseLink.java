package com.ssm.mybatis.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 多对多关联对象表的实体类
 * @see StudentPO
 * @see CoursePO
 * @author caimeng
 * @date 2024/12/16 16:45
 */
@Data
public class StudentCourseLink implements Serializable {
    /**
     * 学生对象
     */
    private StudentPO student;
    /**
     * 课程对象
     */
    private CoursePO course;
}
