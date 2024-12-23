package com.ssm.mybatis.plus.config;

import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.ssm.mybatis.plus.handler.ProjectMetaObjectHandler;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author caimeng
 * @date 2024/12/23 9:49
 */
@Configuration
public class MyBatisPlusConfig {

    @Bean
    public MybatisSqlSessionFactoryBean sqlSessionFactoryBean(DataSource dataSource, GlobalConfig globalConfig) {
        MybatisSqlSessionFactoryBean factoryBean = new MybatisSqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        // 设置扫描包
        factoryBean.setTypeAliasesPackage("com.ssm.mybatis.plus.vo");
        factoryBean.setGlobalConfig(globalConfig);
        return factoryBean;
    }

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer configurer = new MapperScannerConfigurer();
        configurer.setBasePackage("com.ssm.mybatis.plus.dao");
        configurer.setAnnotationClass(Mapper.class);
        return configurer;
    }

    /**
     * @return 全局数据配置
     */
    @Bean
    public GlobalConfig.DbConfig dbConfig() {
        GlobalConfig.DbConfig dbConfig = new GlobalConfig.DbConfig();
        // 逻辑删除的内容
        dbConfig.setLogicDeleteValue("1");
        // 逻辑未删除的内容
        dbConfig.setLogicNotDeleteValue("0");
        /*
         * 如果此时所有的逻辑删除字段是统一的名称，也可以在此定义
         * 如果不统一定义，则通过注解定义 (@TableLogic)
         */
//        dbConfig.setLogicDeleteField("status");
        return dbConfig;
    }

    /**
     * 交由spring管理后，在 MybatisSqlSessionFactoryBean 对象创建时注入全局变量 GlobalConfig
     * {@link MyBatisPlusConfig#sqlSessionFactoryBean(DataSource, GlobalConfig)}
     * @param projectMetaObjectHandler 自定义填充器
     * @return 全局配置
     */
    @Bean
    public GlobalConfig globalConfig(ProjectMetaObjectHandler projectMetaObjectHandler) {
        GlobalConfig config = new GlobalConfig();
        config.setMetaObjectHandler(projectMetaObjectHandler);
        return config;
    }
}
