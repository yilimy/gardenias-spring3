package com.example.boot3.jpa.po;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 比 {@link CourseEntity} 多字段
 * @author caimeng
 * @date 2024/6/7 13:47
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "course", schema = "test_sql")
public class CourseWrapper {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long cid;
    @Basic
    private String cname;
    @Basic
    @Temporal(TemporalType.DATE)
    private Date start;
    @Basic
    @Temporal(TemporalType.DATE)
    private Date end;
    @Basic
    private Integer credit;
    @Basic
    private Integer num;
    /**
     *  相比于 CourseEntity 新增的字段
     */
    @Basic
    private String teacher;
    /**
     * 添加 Transient 注解的属性，不会修改数据库表结构
     * 该注解一般不会出现，一般出现在数据维护的时候
     */
    @Transient
    private String school;
}
