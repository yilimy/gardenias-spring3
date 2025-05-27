package com.gardenia.database.config;

import com.gardenia.database.StartDataBaseApplication;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.sql.DataSource;

/**
 * 多数据源测试
 * @author caimeng
 * @date 2025/5/27 13:44
 */
@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@SpringBootTest(classes = StartDataBaseApplication.class)
public class DruidMultiDataSourceConfigTest {
    @Autowired
    @Qualifier("testSqlDataSource")
    private DataSource testSqlDataSource;
    @Autowired
    @Qualifier("testQqqDataSource")
    private DataSource testQqqDataSource;

    @SneakyThrows
    @Test
    public void connectionTest() {
        // com.mysql.cj.jdbc.ConnectionImpl@432448
        System.out.println(testSqlDataSource.getConnection());
        // com.mysql.cj.jdbc.ConnectionImpl@4d964c9e
        System.out.println(testQqqDataSource.getConnection());
    }
}
