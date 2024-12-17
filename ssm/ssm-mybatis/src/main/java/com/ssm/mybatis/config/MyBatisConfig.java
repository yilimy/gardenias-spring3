package com.ssm.mybatis.config;

import lombok.SneakyThrows;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * MyBatis配置类
 * @author caimeng
 * @date 2024/12/17 10:24
 */
@Configuration
public class MyBatisConfig {
    /**
     * SqlSessionFactoryBean是Spring管理的对象，主要功能就是获取 SqlSessionFactory
     * 用来代替 mybatis.cfg.xml
     * @return SqlSessionFactoryBean
     */
    @SneakyThrows
    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean(DataSource dataSource) {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        /*
         * 配置数据源
         * 整体的配置现在全部交由Spring负责，而不再是通过MyBatis配置文件(mybatis.cfg.xml)设置数据源了
         */
        factoryBean.setDataSource(dataSource);
        /*
         * 别名扫描的包，也就是Mapper.xml中可以直接使用实体类的简单名，而不是全路径名。
         * 虽然现在提倡的是“零配置”，但MyBatis仍然建议保留Mapper映射文件
         */
        factoryBean.setTypeAliasesPackage("com.ssm.mybatis.vo");
        /*
         * mybatis.cfg.xml 配置文件是需要在 mappers 标签中分别定义Mapper文件名的，但是现在可以通过扫描路径实现。
         */
        PathMatchingResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
        // 所有Mapper文件的路径匹配
        String mapperPath = PathMatchingResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + "mybatis/mapper/*.xml";
        // Mapper.xml的资源解析配置
        factoryBean.setMapperLocations(patternResolver.getResources(mapperPath));
        return factoryBean;
    }

    /**
     * DAO相关的配置
     * @return MapperScannerConfigurer
     */
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        /*
         * 传统的数据层开发，是通过DAO调用MyBatis，但是这个操作很重复
         * Spring 针对MyBatis的实现可以自动生成代理类，这个代理类会自动调用映射配置SQL(statement)
         */
        MapperScannerConfigurer scannerConfigurer = new MapperScannerConfigurer();
        // DAO 程序包路径
        scannerConfigurer.setBasePackage("com.ssm.mybatis.mapper");
        // 一般良好的做法都需要在映射DAO上配置有映射注解（可以没有）
        scannerConfigurer.setAnnotationClass(Mapper.class);
        /*
         * 对于后续SpringBoot与MyBatis整合机制，是存在有“Mapper.class”注解映射的，
         * 现在只是进行Spring的开发整合，可以不用配置注解。
         */
        return scannerConfigurer;
    }
}
