package com.example.boot3.jdbc.config;

import cn.hutool.core.util.RandomUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.stream.Stream;

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

    /**
     * 参数批处理插入
     */
    @SneakyThrows
    @Test
    public void batchTest() {
        // List.of 1.9
        List<String> books = List.of("Spring开发实战", "SSM开发实战", "Netty开发实战", "Mybatis开发实战", "Redis开发实战", "Dubbo开发实战");
        List<Double> prices = Stream.generate(() -> RandomUtil.randomDouble(500, 2, RoundingMode.UP)).limit(6).toList();
        String insert = "INSERT INTO book(title, author, price) VALUES (?, ?, ?)";
        int[] results = jdbcTemplate.batchUpdate(insert, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, books.get(i));
                ps.setString(2, "NPC");
                ps.setDouble(3, prices.get(i));
            }

            @Override
            public int getBatchSize() {
                return books.size();
            }
        });
        log.info("results = {}", results);
    }

    /**
     * 对象批处理插入
     * <p>
     *     场景：
     *     定义一个凌晨12点任务，通过某个系统抓取数据（CVS, JSON, XML, EXCEL）
     *     当需要写入时，一般是1000条左右写入一次（根据实际环境测试决定参数），分批次进行写入
     */
    @SneakyThrows
    @Test
    public void batchObjectTest() {
        List<Object[]> objects = List.of(
                new Object[]{"Spring开发实战", "NPCObject", 11.2},
                new Object[]{"SSM开发实战", "NPCObject", 12.3},
                new Object[]{"MQ开发实战", "NPCObject", 13.4},
                new Object[]{"SC开发实战", "NPCObject", 14.5},
                new Object[]{"BBC开发实战", "NPCObject", 15.6},
                new Object[]{"QT开发实战", "NPCObject", 26.7}
        );
        String insert = "INSERT INTO book(title, author, price) VALUES (?, ?, ?)";
        int[] ints = jdbcTemplate.batchUpdate(insert, objects);
        log.info("ints = {}", ints);
    }
}
