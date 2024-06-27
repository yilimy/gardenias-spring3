package com.example.boot3.jpa.po;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
 * @date 2024/6/26 18:33
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "members")
@Data
@Entity
public class Role implements Serializable {
    @Id
    private String rid;
    private String name;
    /**
     * 一个角色属于多个用户
     */
    @ManyToMany
    private List<Member> members;

    public Role(String rid) {
        this.rid = rid;
    }
}
