/**
 * 使用spring对mybatis进行管理
 * <p>
 *     整合步骤
 *     1. 引入依赖 【00678e78ee9b7490】
 *          - org.mybatis:mybatis-spring
 *          - org.springframework:spring-jdbc
 *          - org.springframework:spring-tx
 *          - org.springframework:spring-aspects
 *          - com.zaxxer:HikariCP
 *     2. 数据库连接池配置
 *          {@link com.ssm.mybatis.config.DataSourceConfig}
 *     3. 事务和切面配置
 *          {@link com.ssm.mybatis.config.TransactionAdviceConfig}
 *     4. 启动类
 *          {@link com.ssm.mybatis.StartMyBatisApplication}
 *     5. 创建MyBatis核心配置类
 * @author caimeng
 * @date 2024/12/17 10:18
 */
package com.ssm.mybatis;