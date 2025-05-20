package com.gardenia.database;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * 测试 Druid 连接
 * @author caimeng
 * @date 2025/5/20 11:44
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = StartDataBaseApplication.class)
public class ConnectionTest {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private DruidDataSource druidDataSource;

    @SneakyThrows
    @Test
    public void connectionTest() {
        System.out.println(druidDataSource.getConnection());
    }
}
