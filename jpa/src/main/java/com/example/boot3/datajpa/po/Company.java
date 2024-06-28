package com.example.boot3.datajpa.po;

import jakarta.persistence.Cacheable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author caimeng
 * @date 2024/6/27 16:47
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Cacheable
public class Company implements Serializable {
    @Id
    // 增长策略：数据库自增
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cid;
    private String name;
    /**
     * 查询出来的结果格式类型为：科学计数法， e.g. 3.0E7
     * 如果要改变数值类型，可以试试 {@link java.math.BigDecimal}
     */
    private Double capital;
    private String place;
    private Integer num;
}
