package com.example.boot3.jpa.po;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


/**
 * 表 course 对应的实体类
 * @author caimeng
 * @date 2024/6/4 15:23
 */
@Data
// Hibernate 是通过反射来创建关系映射的，所以必须要有无参构造器
@NoArgsConstructor
@AllArgsConstructor
@Builder
// 根据该注解扫描？
@Entity
// 如果类名称与表名称一致，则不需要定义 Table.name
@Table(name = "course", schema = "test_sql")
public class CourseEntity {
    // 生成策略: 交由数据库生成
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // 定义主键标记
    @Id
    /*
     * 如果字段名称和属性名称一致，则不需要 @Column 注解。
     * 尽量保持一致，避免不必要的注解
     */
//    @Column(name = "cid")
    private long cid;
    @Basic
    private String cname;
    @Basic
    // 表示日期状态的数据
    @Temporal(TemporalType.DATE)
    private Date start;
    @Basic
    @Temporal(TemporalType.DATE)
    private Date end;
    @Basic
    private Integer credit;
    @Basic
    private Integer num;
    // =================== 2024-06-20 追加，版本号 ===================
    @Version
    private Integer vseq;
}
