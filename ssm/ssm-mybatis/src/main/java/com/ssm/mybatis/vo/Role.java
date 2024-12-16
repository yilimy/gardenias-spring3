package com.ssm.mybatis.vo;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author caimeng
 * @date 2024/12/16 15:14
 */
@Data
public class Role implements Serializable {
    /**
     * 角色ID
     */
    private String rid;
    /**
     * 角色名称
     */
    private String name;
    /**
     * 权限列表
     * 一对多
     */
    @ToString.Exclude
    private List<Action> actions;
}
