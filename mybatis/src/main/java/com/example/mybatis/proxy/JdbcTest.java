package com.example.mybatis.proxy;

import com.example.mybatis.po.User;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * 测试JDBC
 * @author caimeng
 * @date 2024/5/10 18:13
 */
@Slf4j
public class JdbcTest {
    static {
        try {
            // 加载mysql驱动，不写也不报错
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            log.error("加载mysql驱动失败", e);
        }
    }

    @Test
    @SneakyThrows
    public void jdbcTest() {
        // 1. 创建数据库连接
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://192.168.200.130:3306/test_sql?useUnicode=true&allowMultiQuerie=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&useSSL=false",
                "root", "Gm02_prd8!");
        // 2. 构造Statement
        PreparedStatement statement = connection.prepareStatement("select * from user where name=?");
        statement.setString(1, "Jack");
        // 3. 执行sql
        statement.execute();
        // 4. 封装结果
        List<User> list = new ArrayList<>();
        ResultSet resultSet = statement.getResultSet();
        User user;
        while (resultSet.next()) {
            user = new User();
            user.setId(resultSet.getInt("id"));
            user.setName(resultSet.getString("name"));
            user.setAge(resultSet.getInt("age"));
            list.add(user);
        }
        System.out.println(list);
        // 5. 关闭数据库连接
        connection.close();
    }
}
