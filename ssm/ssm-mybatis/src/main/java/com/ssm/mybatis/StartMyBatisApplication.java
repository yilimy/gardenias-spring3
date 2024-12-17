package com.ssm.mybatis;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author caimeng
 * @date 2024/12/17 10:15
 */
@ComponentScan("com.ssm.mybatis")   // 定义扫描路径
// 现在MyBatis交由Spring进行事务管理，所以一定要编写如下的注解，如果不编写，那么事务不生效
@EnableAspectJAutoProxy     // 启用AOP动态代理
public class StartMyBatisApplication {
}
