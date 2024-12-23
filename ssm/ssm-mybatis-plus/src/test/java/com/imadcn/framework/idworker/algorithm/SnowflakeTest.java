package com.imadcn.framework.idworker.algorithm;

import org.junit.jupiter.api.Test;

/**
 * @author caimeng
 * @date 2024/12/23 15:51
 */
public class SnowflakeTest {
    @Test
    public void genIdTest() {
        Snowflake snowflake = Snowflake.create(0);
        long id = snowflake.nextId();
        // 1055875681101021212
        // 769134966817820672 （视频中生成的id）
        System.out.println(id);
    }
}
