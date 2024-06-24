package com.example.boot3.jpa.po.lazy;

import com.example.boot3.jpa.po.Login;
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
 * {@link Login} 的懒加载模式
 * @author caimeng
 * @date 2024/6/24 11:17
 */
@ToString(exclude = "details")
@Entity
@Table(name = "login")
@Data
public class LoginLazy implements Serializable {
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
            // 相比于原对象,改为懒加载模式
            fetch = FetchType.LAZY
    )
    private DetailsLazy details;
}
