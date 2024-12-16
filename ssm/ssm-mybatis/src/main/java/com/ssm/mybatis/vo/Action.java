package com.ssm.mybatis.vo;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author caimeng
 * @date 2024/12/16 15:15
 */
@Data
public class Action implements Serializable {
    /**
     * 权限ID
     */
    private String aid;
    /**
     * 权限名称
     */
    private String name;
    /**
     * 角色ID
     */
    @ToString.Exclude
    private Role role;
}
