package com.ssm.mybatis.plus.config;

import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.ssm.mybatis.plus.generator.SnowFlakeIdGenerator;
import com.ssm.mybatis.plus.handler.ProjectMetaObjectHandler;
import com.ssm.mybatis.plus.injector.MySqlInjector;
import com.ssm.mybatis.plus.interceptor.MyInnerInterceptor;
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
    public MybatisSqlSessionFactoryBean sqlSessionFactoryBean(
            DataSource dataSource,
            GlobalConfig globalConfig,
            MybatisPlusInterceptor mybatisPlusInterceptor
    ) {
        MybatisSqlSessionFactoryBean factoryBean = new MybatisSqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        // 设置扫描包
        factoryBean.setTypeAliasesPackage("com.ssm.mybatis.plus.vo");
        factoryBean.setGlobalConfig(globalConfig);
        factoryBean.setPlugins(mybatisPlusInterceptor);
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
     * {@link MyBatisPlusConfig#sqlSessionFactoryBean(DataSource, GlobalConfig, MybatisPlusInterceptor)}
     * @param projectMetaObjectHandler 自定义填充器
     * @return 全局配置
     */
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Bean
    public GlobalConfig globalConfig(
            ProjectMetaObjectHandler projectMetaObjectHandler,  // 数据填充器
            SnowFlakeIdGenerator identifierGenerator,     // id 生成器
            MySqlInjector injector       // 自定义的SQL注入器
    ) {
        GlobalConfig config = new GlobalConfig();
        config.setMetaObjectHandler(projectMetaObjectHandler);
        config.setIdentifierGenerator(identifierGenerator);
        config.setSqlInjector(injector);
        return config;
    }

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 追加自定义的拦截器
        interceptor.addInnerInterceptor(new MyInnerInterceptor());
        return interceptor;
    }
}
