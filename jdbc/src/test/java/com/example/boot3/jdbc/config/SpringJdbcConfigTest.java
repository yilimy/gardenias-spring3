package com.example.boot3.jdbc.config;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author caimeng
 * @date 2024/5/20 17:53
 */
@ContextConfiguration(classes = {HikariDataSourceConfig.class, SpringJdbcConfig.class})
// Junit 5
@ExtendWith(SpringExtension.class)
@Slf4j
public class SpringJdbcConfigTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @SneakyThrows
    @Test
    public void jdbcTemplateInitTest() {
        log.info("JDBC操作模板对象: {}", jdbcTemplate);
    }

    /**
     * 该操作不安全，需要对sql进行预处理，防止sql注入
     * @see SpringJdbcConfigTest#insertTestPlus()
     */
    @Test
    public void insertTest() {
        String insert = "INSERT INTO book(title, author, price) VALUES ('JAVA从入门到入土', '小王', '996')";
        // 执行sql语句
        log.info("SQL增加命令: {}", jdbcTemplate.update(insert));
    }

    @Test
    public void insertTestPlus() {
        String insert = "INSERT INTO book(title, author, price) VALUES (?, ?, ?)";
        // 执行sql语句(预处理)
        log.info("SQL增加命令: {}", jdbcTemplate.update(insert, "Python从入门到放弃", "小明", "007"));
    }

    @Test
    public void deleteTest() {
        String del = "DELETE FROM book WHERE bid = ?";
        // 返回影响的数据行数   e.g. bid 没查到时返回0
        log.info("SQL删除命令: {}", jdbcTemplate.update(del, 2));
    }
}
