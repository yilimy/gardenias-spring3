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
 * @author caimeng
 * @date 2024/5/20 16:07
 */
// 加载指定配置文件，可以换成 ComponentScan
@ContextConfiguration(classes = PolarDBSourceConfig.class)
// Junit 5
@ExtendWith(SpringExtension.class)
@Slf4j
public class PolarDBSourceConfigTest {
    @Autowired
    private DataSource dataSource;

    /**
     * 测试：数据库连接
     */
    @SneakyThrows
    @Test
    public void connectionTest() {
        Connection connection = dataSource.getConnection();
        log.info("数据库连接对象: {}", connection);
    }
}
