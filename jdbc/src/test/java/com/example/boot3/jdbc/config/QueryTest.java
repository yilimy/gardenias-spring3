package com.example.boot3.jdbc.config;

import cn.hutool.core.util.RandomUtil;
import com.example.boot3.jdbc.pojo.Book;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

/**
 * 数据库查询测试
 * 在数据库操作过程中，除了数据库更新操作外，最为繁琐的就是数据库查询功能了。
 * 由于JdbcTemplate设计定位属于ORMapping组件，所以需要在查询完成后，可以自动将查询结果转换为VO类型的实例。
 * 为了解决该问题，在Spring JDBC 中提供了一个 RowMapper 接口，该接口提供有一个 mapRow() 处理方法，可以接收查询结果每行数据的结果集，
 * 用户可以将指定的列取出，并保存在VO实例之中。
 * @author caimeng
 * @date 2024/5/27 10:34
 */
@Slf4j
@ContextConfiguration(classes = {SpringJdbcConfig.class, HikariDataSourceConfig.class})
@ExtendWith(SpringExtension.class)
public class QueryTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 错误的示例
     * 对照组 {@link this#queryPriceById()}
     * @deprecated 错误的示例
     */
    @Test
    @Deprecated
    public void queryById() {
        String sql = "select bid, title, author, price from book where bid=?";
        /*
         * 错误用法，
         * 这里的 Class<T> requiredType 实际是列数据，不是接收的映射对象
         */
        Book book = jdbcTemplate.queryForObject(sql, Book.class, 3);
        log.info("book = {}", book);
    }

    @Test
    public void queryPriceById() {
        String sql = "select price from book where bid=?";
        /*
         * 错误用法，
         * 这里的 Class<T> requiredType 实际是列数据，不是接收的映射对象
         */
        Double price = jdbcTemplate.queryForObject(sql, Double.class, 3);
        // 实际是 7.0
        log.info("price = {}", price);
        assert Double.valueOf(7).equals(price) : "价格查询失败";
    }

    /**
     * 如果是全自动化的ORM组件，就不需要开发者如此费力的进行RowMapper接口对象实例化的处理了。
     * 框架的内部都有支持。
     * e.g.
     *      Spring Data JPA
     *      Mybatis
     */
    @Test
    public void queryByIdWithRowMapper() {
        String sql = "select bid, title, author, price from book where bid=?";
        Book book = jdbcTemplate.queryForObject(sql,
                // 此时传入一个ResultSet 接口实例，这个接口可以获取查询结果
                (rs, rowNum) -> {
                    Book vo = new Book();
                    vo.setBid(rs.getLong(1));
                    vo.setTitle(rs.getString(2));
                    vo.setAuthor(rs.getString(3));
                    vo.setPrice(rs.getDouble(4));
                    return vo;
                },
                // 查询指定编号的数据
                3);
        log.info("book = {}", book);
    }

    /**
     * 查询全量数据
     */
    @Test
    public void queryAll() {
        String sql = "select bid, title, author, price from book";
        List<Book> books = jdbcTemplate.query(sql, (rs, rowNum) -> {
            Book vo = new Book();
            vo.setBid(rs.getLong(1));
            vo.setTitle(rs.getString(2));
            vo.setAuthor(rs.getString(3));
            vo.setPrice(rs.getDouble(4));
            return vo;
        });
        log.info("books = {}", books);
    }

    /**
     * 分页查询
     */
    @Test
    public void querySplit() {
        // 当前页
        int currentPage = RandomUtil.randomInt(1, 5);
        log.info("currentPage = {}", currentPage);
        // 每页显示的数据行
        int pageSize = 5;
        String sql = "select bid, title, author, price from book limit ?,?";
        List<Book> books = jdbcTemplate.query(sql, (rs, rowNum) -> {
            Book vo = new Book();
            vo.setBid(rs.getLong(1));
            vo.setTitle(rs.getString(2));
            vo.setAuthor(rs.getString(3));
            vo.setPrice(rs.getDouble(4));
            return vo;
        }, (currentPage - 1) * pageSize, pageSize);
        log.info("books = {}", books);
    }

    /**
     * 统计测试
     */
    @Test
    public void queryCount() {
        String sql = "select count(1) from book where title like ?";
        Long count = jdbcTemplate.queryForObject(sql, Long.class, "%开发%");
        log.info("count = {}", count);
    }
}
