/**
 * 1. 添加依赖
 * 2. 创建 database.properties 资源文件，定义数据库连接信息
 * 3. 定义 jpa.properties 资源文件
 * 4. 创建 DataSourceConfig 配置类，定义数据源
 *      {@link com.example.boot3.spring.cache.config.HikariDataSourceConfig}
 * 5. 创建事务配置类，必须使用 JPA 的事务控制类
 *      {@link com.example.boot3.spring.cache.config.TransactionConfig}
 * 6. 创建 TransactionAdviceConfig 切面配置类
 *      {@link com.example.boot3.spring.cache.config.TransactionAdviceConfig}
 * 7. 创建 JPA 的配置类，注意，这里要配置实体类扫描包 setSharedCacheMode
 *      {@link com.example.boot3.spring.cache.config.SpringDataJPAConfig}
 * 8. 创建启动扫描处理类
 *      {@link com.example.boot3.spring.cache.StartSpringCache}
 * 9. 创建 JPA 持久化处理类
 * 10.创建 DAO 接口层，并继承 JpaRepository
 *      {@link org.springframework.data.jpa.repository.JpaRepository}
 * @author caimeng
 * @date 2024/7/18 15:17
 */
package com.example.boot3.spring.cache;