package com.example.boot3.jpa.po;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


/**
 * 表 course 对应的实体类
 * 与 {@link CourseEntity} 的主键生成方式不同，使用的是UUID
 * @author caimeng
 * @date 2024/6/4 15:23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
/*
 * 表名不能和 CourseEntity 一致，否则如果先创建CourseEntity的表，
 * 在字段cid的数据类型确定后，CourseUUID就不能执行操作了，因为没有删除或修改列的操作（只有新增）
 */
@Table(name = "course_identity", schema = "test_sql")
public class CourseUUID {
    /**
     * 修改主键生成策略(UUID)后，主键类型一般改为String
     */
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    private String cid;
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
}
