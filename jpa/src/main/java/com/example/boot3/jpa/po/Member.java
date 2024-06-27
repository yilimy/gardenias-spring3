package com.example.boot3.jpa.po;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author caimeng
 * @date 2024/6/26 18:32
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "roles")
@Data
@Entity
public class Member implements Serializable {
    @Id
    private String mid;
    private String password;
    /**
     * 一个用户下有多个角色
     * ManyToMany 定义多对多的数据关联
     */
    @ManyToMany
    // 维护关系表
    @JoinTable(
            // 定义关联表的名称
            name = "member_role",
            // 看起来，这里定义的字段名都是 member_role 中的字段
            // `member` join `member_role` on mid，
            joinColumns = {@JoinColumn(name = "mid")},
            /*
             * 在进行多对多处理的时候，要指定一个主动方
             * e.g. 现在要通过用户找到角色
             *      通过 member 找到 role
             */
            inverseJoinColumns ={@JoinColumn(name = "rid")}
    )
    private List<Role> roles;
}
