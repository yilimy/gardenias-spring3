package com.example.boot3.jpa.po;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * 用户登录表
 * @author caimeng
 * @date 2024/6/24 10:01
 */
@ToString(exclude = "details")
@Entity
@Table(name = "login")
@Data
public class Login implements Serializable {
    /**
     * 用户ID
     */
    @Id
    private String uid;
    /**
     * 登录密码
     */
    private String password;
    /**
     * 一对一关联
     */
    @OneToOne(
            // 映射 Details 类中的成员属性
            mappedBy = "login",
            // 全部进行数据的级联操作
            cascade = CascadeType.ALL,
            // 数据同步抓取
            fetch = FetchType.EAGER
    )
    private Details details;
}
