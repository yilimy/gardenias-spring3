package com.example.boot3.jpa.po;

import jakarta.persistence.Basic;
import jakarta.persistence.Cacheable;
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

import java.io.Serializable;
import java.util.Date;

/**
 * 开启二级缓存的 PO 对象
 * 相比于 {@link CourseEntity} 多了二级缓存注解 @Cacheable
 * @author caimeng
 * @date 2024/6/17 15:07
 */
// 该类对象可以实现二级缓存
@Cacheable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "course", schema = "test_sql")
public class CourseCache implements Serializable {
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
}
