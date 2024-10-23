/**
 * 本地IP直接访问Web资源
 * <a href="https://www.bilibili.com/video/BV1EqxGe4Eoz/" />
 * <p>
 *     1. 创建一个实现本地IP访问的投票器
 *          {@link com.example.ssm.mvcb.voter.LocalAccessVoter}
 *     2. 配置类中定义投票管理器的实例
 *          {@link com.example.ssm.mvcb.config.WebMVCSecurityConfiguration#accessDecisionManager()}
 *     3. 在过滤链中进行投票器的配置
 *          {@link com.example.ssm.mvcb.config.WebMVCSecurityConfiguration#filterChain(HttpSecurity, UserDetailsService, JdbcTokenRepositoryImpl, SimpleUrlAuthenticationFailureHandler, AccessDecisionManager)}
 *     4. 在 /pages/message/ 路径中添加了角色 LOCAL_FLAG
 *          {@link com.example.ssm.mvcb.voter.LocalAccessVoter#LOCAL_FLAG}
 *     5. 该代码测试失败，版本变动较大
 * @author caimeng
 * @date 2024/10/23 15:31
 */
package com.example.ssm.security.local;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;