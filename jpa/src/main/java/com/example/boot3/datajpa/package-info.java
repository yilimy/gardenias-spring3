/**
 * DataJPA时代     com.example.boot3.jpa.spring
 * 引入springDataJPA的目的是不再使用xml配置文件，包括原生JPA的核心配置文件 persistence.xml
 * springDataJPA的核心配置类 {@link org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean}
 * <p>
 *     LocalContainerEntityManagerFactoryBean 的核心配置方法
 *     1. {@link org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean#setDataSource(DataSource)}
 *          配置持久化数据源
 *     2. {@link org.springframework.orm.jpa.AbstractEntityManagerFactoryBean#setPersistenceProvider(PersistenceProvider)}
 *          配置持久化实现组件
 *     3. {@link org.springframework.orm.jpa.AbstractEntityManagerFactoryBean#setJpaVendorAdapter(JpaVendorAdapter)}
 *          配置Hibernate实现JPA适配器
 *     4. {@link org.springframework.orm.jpa.AbstractEntityManagerFactoryBean#setJpaDialect(JpaDialect)}
 *          配置JPA数据库方言
 *     5. {@link org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean#setPackagesToScan(String...)}
 *          配置实体类扫描包
 *     6. {@link org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean#setPersistenceUnitName(String)}
 *          定义持久化单元名称
 *     7. {@link org.springframework.orm.jpa.AbstractEntityManagerFactoryBean#getJpaPropertyMap()}
 *          配置JPA相关属性
 *     8. {@link org.springframework.orm.jpa.AbstractEntityManagerFactoryBean#setJpaProperties(Properties)}
 *          定义JPA相关属性
 *     9. {@link org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean#setPersistenceXmlLocation(String)}
 *          配置 persistence.xml 资源加载路径
 *     10.{@link org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean#setSharedCacheMode(SharedCacheMode)}
 *          设置缓存模式
 * <p>
 *     由于当前的项目中要进行数据库的编写定义，所以引入 database.properties 资源文件
 *     {@link com.example.boot3.datajpa.config.HikariDataSourceConfig}
 * <p>
 *     创建AOP的事务管理类，需要替换为JPA的事务管理类
 *     {@link com.example.boot3.datajpa.config.TransactionConfig#transactionManager(DataSource)}
 * <p>
 *     如果要启用JPA的配置，那么就要创建一个jpa.properties配置文件，在该文件中定义JPA属性项。
 *     {@link com.example.boot3.datajpa.config.SpringDataJPAConfig}
 * @author caimeng
 * @date 2024/6/27 14:30
 */
package com.example.boot3.datajpa;

import jakarta.persistence.SharedCacheMode;
import jakarta.persistence.spi.PersistenceProvider;
import org.springframework.orm.jpa.JpaDialect;
import org.springframework.orm.jpa.JpaVendorAdapter;

import javax.sql.DataSource;
import java.util.Properties;