package com.example.ssm.mvcb.dao.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;

import javax.sql.DataSource;

/**
 * Token持久化配置
 * @author caimeng
 * @date 2024/10/18 11:32
 */
@Configuration
public class TokenRepositoryConfig {

    @Bean
    public JdbcTokenRepositoryImpl  jdbcTokenRepository(DataSource dataSource) {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }
}
