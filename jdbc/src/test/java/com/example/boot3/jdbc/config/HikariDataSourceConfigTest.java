package com.example.boot3.jdbc.config;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * HikariCP数据库连接池测试
 * @author caimeng
 * @date 2024/5/20 17:34
 */
@ContextConfiguration(classes = HikariDataSourceConfig.class)
// Junit 5
@ExtendWith(SpringExtension.class)
@Slf4j
public class HikariDataSourceConfigTest {
    @Autowired
    private DataSource dataSource;
    /**
     * 测试：数据库连接
     */
    @SneakyThrows
    @Test
    public void connectionTest() {
        Connection connection = dataSource.getConnection();
        log.info("Hikari数据库连接对象: {}", connection);
    }
}
