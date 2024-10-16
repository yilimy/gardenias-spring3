package com.example.ssm.mvcb.service;

import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;

import java.util.List;

/**
 * @author caimeng
 * @date 2024/10/14 18:48
 */
public interface IAdminService {
    /**
     * 这里需要填入一个SpEL的表达式
     * @see org.springframework.security.access.expression.SecurityExpressionRoot
     * PreAuthorize 在业务调用之前进行授权检查
     * @return 添加结果
     */
    @PreAuthorize("hasRole('ADMIN')")
    boolean add();

    /**
     * 要同时满足有两个角色存在才能访问该业务接口
     * @return 编辑结果
     */
    @PreAuthorize("hasRole('ADMIN') and hasRole('SYSTEM')")
    boolean edite();

    /**
     * 数据删除
     * <p>
     *     测试SpringSecurity的过滤注解
     *          只有参数中的“yootk”会传入接口
     *          - AdminAction - ids=[muyan, lee]
     *            执行了业务删除, ids=[]
     *          - AdminAction - ids=[muyan, yootk, lee]
     *            执行了业务删除, ids=[yootk]
     * @param ids 删除操作
     * @return 执行结果
     */
    @PreFilter(filterTarget = "ids", value = "filterObject.contains('yootk')")
    Object delete(List<String> ids);

    /**
     * 测试 secured 注解
     * <p>
     *     拥有 ROLE_SYSTEM 或 ROLE_ADMIN 角色的用户允许该业务的调用
     * @param username 用户名
     * @return 信息
     */
    @Secured({ "ROLE_SYSTEM", "ROLE_ADMIN" })
    String get(String username);

    /**
     * 测试jsr-250安全注解标准
     * 拥有角色 SYSTEM 和 ADMIN 中的任一个就能访问
     * @return 结果
     */
    @RolesAllowed({"SYSTEM", "ADMIN"})
    Object list();
}
