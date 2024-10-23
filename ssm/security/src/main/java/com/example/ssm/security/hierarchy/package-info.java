/**
 * RoleHierarchy与角色继承
 * <a href="https://www.bilibili.com/video/BV1JvxGeZEz8/" />
 * <p>
 *     1. 设置角色层级关系
 *          {@link com.example.ssm.mvcb.config.WebMVCSecurityConfiguration#roleHierarchy()}
 *     2. 追加表达式的支持
 *          {@link com.example.ssm.mvcb.config.WebMVCSecurityConfiguration#securityExpressionHandler(RoleHierarchy)}
 *     3. 修改投票管理器的配置
 *          {@link com.example.ssm.mvcb.config.WebMVCSecurityConfiguration#accessDecisionManager(SecurityExpressionHandler)}
 *     4. 实现不了
 * @author caimeng
 * @date 2024/10/23 16:48
 */
package com.example.ssm.security.hierarchy;

import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;