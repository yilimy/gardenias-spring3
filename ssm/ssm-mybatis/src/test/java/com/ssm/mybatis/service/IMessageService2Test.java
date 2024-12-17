package com.ssm.mybatis.service;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

/**
 * @author caimeng
 * @date 2024/12/17 15:42
 */
public class IMessageService2Test {

    /**
     * 测试：mapper.xml文件匹配
     */
    @Test
    @SneakyThrows
    public void resourcePathTest() {
        PathMatchingResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
        Resource[] resource = patternResolver.getResources("classpath*:mybatis/mapper/*.xml");
        System.out.println(resource.length);
    }
}
