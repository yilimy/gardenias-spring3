package com.example.boot3.jdbc.config;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.PreparedStatement;
import java.sql.Statement;

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

    /**
     * 在Mysql数据库中，有一种功能，可以通过next()处理函数来获取当前生成ID号（主要针对自增长主键）
     * 实际上这个功能的主要目的是为了解决增加数据时的ID返回问题，
     * 因为很多时候需要在成功增加数据后，对指定ID的数据进行控制，所以才提供了专属函数
     * Oracle中直接使用序列即可，但是Mysql就要专属函数。
     * <p>
     *     在程序开发中，如果想要获取到增长后的ID，在 spring JDBC 中提供有一个KeyHolder接口，在这个接口里定义了获取主键内讧的处理方法。
     * <p>
     *     由于 Spring JDBC 属于轻量级的设计开发，在一些重量级的设计开发组件里，会自动包含此功能。
     *     e.g. JPA/Hibernate、Mybatis等
     */
    @SneakyThrows
    @Test
    public void keyHolderTest() {
        String insert = "INSERT INTO book(title, author, price) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int count = jdbcTemplate.update(con -> {
            PreparedStatement pstmt = con.prepareStatement(insert,
                    // 设定返回主键
                    Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, "C#从入门到放飞");
            pstmt.setString(2, "小张");
            pstmt.setDouble(3, 108.52);
            return pstmt;
        }, keyHolder);
        log.info("SQL更新数据行数:{}, 当前数据ID:{}", count, keyHolder.getKey());
    }
}
