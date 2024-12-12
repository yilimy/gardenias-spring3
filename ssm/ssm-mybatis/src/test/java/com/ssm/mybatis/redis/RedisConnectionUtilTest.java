package com.ssm.mybatis.redis;

import io.lettuce.core.api.StatefulConnection;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author caimeng
 * @date 2024/12/11 18:35
 */
@Slf4j
public class RedisConnectionUtilTest {
    /**
     * 测试：redis连接 (lettuce)
     */
    @Test
    public void connectionTest() {
        StatefulConnection<byte[], byte[]> connection = RedisConnectionUtil.getConnection();
        assert connection != null : "创建redis连接失败";
        RedisConnectionUtil.close();
    }
}
