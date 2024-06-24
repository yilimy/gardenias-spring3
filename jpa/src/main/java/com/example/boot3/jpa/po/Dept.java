package com.example.boot3.jpa.po;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * 部门表
 * @author caimeng
 * @date 2024/6/7 15:54
 */
@Data
@ToString(exclude = "emps")
@NoArgsConstructor
@Entity
@Table(name = "dept")
public class Dept {
    @Id
    private long deptno;
    /**
     * 部门名称
     */
    private String dname;
    /**
     * 部门位置
     */
    private String loc;
    /**
     * 一个部门有多个雇员
     */
    @OneToMany(
        // 雇员的成员属性里包含有dept的内容
        mappedBy = "dept",
        // 更新和查询全部级联
        cascade = CascadeType.ALL
    )
    private List<Emp> emps;

    public Dept(long deptno, String dname, String loc) {
        this.deptno = deptno;
        this.dname = dname;
        this.loc = loc;
    }
}
