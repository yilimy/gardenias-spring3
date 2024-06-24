package com.example.boot3.jpa.po.eager;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author caimeng
 * @date 2024/6/24 11:55
 */
@ToString(exclude = "dept")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "emp")
@Data
public class EmpEager implements Serializable {
    /**
     * 雇员编号
     */
    @Id
    private Long empno;
    /**
     * 雇员名称
     */
    private String ename;
    /**
     * 基本工资
     */
    private Double sal;
    // 这里为什么不是 deptno, 而是用 Dept 对象替换的?
//    private Long deptno;
    /**
     * 一个雇员属于一个部门
     */
    // 多个雇员属于一个部门
    @ManyToOne
    // 定义关联字段,如果现在属性和字段一致,写一个就可以了
    @JoinColumn(name = "deptno")
    private DeptEager dept;
}
