package com.gardenia.database;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

/**
 * 测试 Druid 连接
 * @author caimeng
 * @date 2025/5/20 11:44
 */
@SuppressWarnings("SpringBootApplicationProperties")
@ExtendWith(SpringExtension.class)
@SpringBootTest(
        classes = StartDataBaseApplication.class,
        properties = {
                "spring.test-qqq.datasource.enabled=true",
        }
)
public class ConnectionTest {
    @Autowired
    private List<DruidDataSource> druidDataSources;

    @Autowired(required = false)    // 自定义的 Druid 连接池，需要 spring.test-qqq.datasource.enabled=true
    @Qualifier("qqqDruidDataSource")
    private DruidDataSource qqqDruidDataSource;

    @SneakyThrows
    @Test
    public void connectionTest() {
        System.out.println(druidDataSources.get(0).getConnection());
        if (qqqDruidDataSource != null) {
            System.out.println(qqqDruidDataSource.getConnection());
        }
    }
}
