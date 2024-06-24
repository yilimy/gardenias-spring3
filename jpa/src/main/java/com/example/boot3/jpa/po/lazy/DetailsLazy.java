package com.example.boot3.jpa.po.lazy;

import com.example.boot3.jpa.po.Details;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * {@link Details} 的懒加载模式
 * @author caimeng
 * @date 2024/6/24 10:04
 */
@ToString(exclude = "login")
@Entity
@Table(name = "details")
@Data
public class DetailsLazy implements Serializable {
    /**
     * 用户ID
     */
    @Id
    private String uid;
    /**
     * 用户名称
     */
    private String name;
    /**
     * 用户年龄
     */
    private Integer age;
    /**
     * 一对一关联
     * 比原对象多了懒加载配置
     */
    @OneToOne(fetch = FetchType.LAZY)
    // 关联字段的配置
    @JoinColumn(
            // Login 类的关联成员属性配置
            name = "uid",
            // login数据表之间的关联字段的配置
            referencedColumnName = "uid",
            // 数据唯一
            unique = true
    )
    private LoginLazy login;
}
