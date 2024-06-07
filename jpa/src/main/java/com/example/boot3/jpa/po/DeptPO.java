package com.example.boot3.jpa.po;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.TableGenerator;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 通过主键表生成主键
 * 主键表sql参考: test/resources/sql/identity_table.sql
 * @author caimeng
 * @date 2024/6/7 15:54
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "dept")
public class DeptPO {
    @Id
    @TableGenerator(
            // 定义当前主键生成配置的名称
            name = "DEPT_GENERATOR",
            // 主键存放的表
            table = "table_id_generate",
            // 主键列名称
            pkColumnName = "id_key",
            // 主键行数据
            pkColumnValue = "DEPT_ID",
            // 主键值的字段
            valueColumnName = "id_value",
            // 主键的增长步长
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "DEPT_GENERATOR")
    private long deptno;
    private String dname;

    public DeptPO(String dname) {
        this.dname = dname;
    }
}
