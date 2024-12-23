package com.ssm.mybatis.plus.generator;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.imadcn.framework.idworker.algorithm.Snowflake;
import org.springframework.stereotype.Component;

/**
 * 雪花ID生成器
 * @author caimeng
 * @date 2024/12/23 15:57
 */
@Component
public class SnowFlakeIdGenerator implements IdentifierGenerator {
    @Override
    public Number nextId(Object entity) {
        return Snowflake.create(0).nextId();
    }

}
