package com.gardenia.database.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.gardenia.database.config.mp.FlagMetaObjectHandler;
import lombok.SneakyThrows;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.sql.DataSource;

/**
 * @author caimeng
 * @date 2025/5/26 10:28
 */
@Configuration
public class MybatisPlusConfig {

    @Bean
    public MybatisPlusInterceptor iMybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

    // ----------------- 以下是在没有com.baomidou:mybatis-plus-boot-starter自动装配下的情况 -----------------
    /**
     * 以Bean注入的方式，进行MybatisPlus的手工配置。
     * 在自动装配中参考 sqlSessionFactory 的注入
     * {@link com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration#sqlSessionFactory(DataSource)}
     * @return MybatisSqlSessionFactoryBean
     */
    @SneakyThrows
    @Bean
    @ConditionalOnProperty(value = "mybatis-plus.auto", havingValue = "false")  // 配置了mybatis-plus.auto=false，才生效
    public MybatisSqlSessionFactoryBean iMybatisSqlSessionFactoryBean(
            @Autowired DataSource dataSource,
            // 设置资源文件的路径
            @Value("${mybatis-plus.config-location}") Resource configLocation,
            // 扫描别名
            @Value("${mybatis-plus.type-aliases-package}") String typeAliasesPackage,
            // 扫描Mapping路径
            @Value("${mybatis-plus.mapper-locations}") String mapperLocations,
            // 逻辑删除的值
            @Value("${mybatis-plus.global-config.db-config.logic-delete-value}") String logicDeleteValue,
            // 逻辑不删除的值
            @Value("${mybatis-plus.global-config.db-config.logic-not-delete-value}") String logicNotDeleteValue) {
        MybatisSqlSessionFactoryBean factoryBean = new MybatisSqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        // 配置程序的扫描类型，主要用于jar包进行文件资源的访问，基于SpringBoot进行扫描
        factoryBean.setVfs(SpringBootVFS.class);
        factoryBean.setConfigLocation(configLocation);
        factoryBean.setTypeAliasesPackage(typeAliasesPackage);
        // setMapperLocations需要一个数组，因此要对注入的文件路径进行解析
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resourcePatternResolver.getResources(mapperLocations);
        factoryBean.setMapperLocations(resources);
        // 设置分页插件
        factoryBean.setPlugins(iMybatisPlusInterceptor());
        // 设置全局配置
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setDbConfig(new GlobalConfig.DbConfig());
        globalConfig.getDbConfig().setLogicDeleteValue(logicDeleteValue);
        globalConfig.getDbConfig().setLogicNotDeleteValue(logicNotDeleteValue);
        // 设置自动填充项
        globalConfig.setMetaObjectHandler(new FlagMetaObjectHandler());
        factoryBean.setGlobalConfig(globalConfig);
        return factoryBean;
    }
}
