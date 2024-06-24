package com.example.boot3.jpa.po.eager;

import com.example.boot3.jpa.po.Dept;
import com.example.boot3.jpa.po.Emp;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * 相比于 {@link Dept} 变更了数据抓取方式
 * @author caimeng
 * @date 2024/6/24 16:58
 */
@Data
@ToString(exclude = "emps")
@NoArgsConstructor
@Entity
@Table(name = "dept")
public class DeptEager {
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
            cascade = CascadeType.ALL,
            /*
             * 相比于原 Dept 类,变更了抓取方式.
             * 数据一起加载
             */
            fetch = FetchType.EAGER
    )
    private List<Emp> emps;

    public DeptEager(long deptno, String dname, String loc) {
        this.deptno = deptno;
        this.dname = dname;
        this.loc = loc;
    }
}
