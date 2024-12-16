package com.ssm.mybatis.vo;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author caimeng
 * @date 2024/12/16 16:34
 */
@Data
public class StudentPO implements Serializable {
    /**
     * 学生ID
     */
    private String sid;
    /**
     * 学生名称
     */
    private String name;
    /**
     * 学生所选择的课程信息
     */
    @ToString.Exclude
    private List<CoursePO> courses;
}
