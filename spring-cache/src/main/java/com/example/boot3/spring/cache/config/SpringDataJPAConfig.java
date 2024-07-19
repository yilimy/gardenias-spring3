package com.example.boot3.spring.cache.config;

import com.example.boot3.spring.cache.util.Constants;
import jakarta.persistence.SharedCacheMode;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;

/**
 * SpringDataJPA配置中心
 * 参考Hibernate的可用属性配置 {@link AvailableSettings}
 * @author caimeng
 * @date 2024/6/27 16:00
 */
@Configuration
@PropertySource("classpath:config/jpa.properties")
public class SpringDataJPAConfig {
    @Value("${hibernate.hbm2ddl.auto:update}")
    private String hbm2ddlAuto;
    @Value("${hibernate.show_sql:true}")
    private Boolean showSql;
    @Value("${hibernate.format_sql:false}")
    private Boolean formatSql;
//    @Value("${hibernate.cache.use_second_level_cache:true}")
//    private Boolean useSecondLevelCache;
//    @Value("${hibernate.cache.region.factory_class:org.hibernate.cache.jcache.internal.JCacheRegionFactory}")
//    private String factoryClass;
//    @Value("${hibernate.javax.cache.provider:org.ehcache.jsr107.EhcacheCachingProvider}")
//    private String cacheProvider;

    /**
     * @return JPA持久化的实现类
     */
    @Bean
    public HibernatePersistenceProvider hibernatePersistenceProvider() {
        return new HibernatePersistenceProvider();
    }

    /**
     * @return JPA的适配器
     */
    @Bean
    public HibernateJpaVendorAdapter hibernateJpaVendorAdapter() {
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setShowSql(showSql);
        return adapter;
    }

    /**
     * @return JPA方言
     */
    @Bean
    public HibernateJpaDialect hibernateJpaDialect() {
        return new HibernateJpaDialect();
    }

    /**
     * 进行Bean的注册
     * <p>
     *     注意： 该bean必须命名为 entityManagerFactory 否则会提示报错
     *          org.springframework.beans.factory.NoSuchBeanDefinitionException:
     *                  No bean named 'entityManagerFactory' available
     * @return entityManagerFactoryBean
     */
//    @Bean
    @Bean("entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(
            DataSource dataSource,
            HibernatePersistenceProvider provider,
            HibernateJpaVendorAdapter adapter,
            HibernateJpaDialect dialect) {
        // 工厂Bean
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setPersistenceProvider(provider);
        factoryBean.setJpaVendorAdapter(adapter);
        factoryBean.setJpaDialect(dialect);
        // 在当前应用之中是直接提供了二级缓存的配置，所以应该继续启用
        factoryBean.setSharedCacheMode(SharedCacheMode.ENABLE_SELECTIVE);
        // 实体类的扫描包
        factoryBean.setPackagesToScan(Constants.ENTITY_PACKAGE);
        // 持久化单元的处理名称
        factoryBean.setPersistenceUnitName("DataJPA");
        // 自定义的JPA配置项
        factoryBean.getJpaPropertyMap().put(AvailableSettings.HBM2DDL_AUTO, hbm2ddlAuto);
        factoryBean.getJpaPropertyMap().put(AvailableSettings.SHOW_SQL, showSql);
        factoryBean.getJpaPropertyMap().put(AvailableSettings.FORMAT_SQL, formatSql);
//        factoryBean.getJpaPropertyMap().put(AvailableSettings.USE_SECOND_LEVEL_CACHE, useSecondLevelCache);
//        factoryBean.getJpaPropertyMap().put(AvailableSettings.CACHE_REGION_FACTORY, factoryClass);
//        factoryBean.getJpaPropertyMap().put("hibernate.javax.cache.provider", cacheProvider);
        return factoryBean;
    }
}
