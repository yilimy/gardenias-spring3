package com.example.boot3.bv1cn4y1o7q3.service;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;

/**
 * 该bean用来测试三个初始化方法的调用顺序
 * <p>
 *     三个初始化方法的执行顺序
 *     1. PostConstruct init ...
 *     2. InitializingBean init ...
 *     3. init-method init ...
 * @author caimeng
 * @date 2024/6/11 15:27
 */
@Slf4j
@Data
public class SequenceService implements InitializingBean {
    private String id;

    @PostConstruct
    public void postInit() {
        log.info("1. PostConstruct init ...");
    }

    @Override
    public void afterPropertiesSet() {
        log.info("2. InitializingBean init ...");
    }

    public void xmlInit() {
        log.info("3. init-method init ...");
    }
}
