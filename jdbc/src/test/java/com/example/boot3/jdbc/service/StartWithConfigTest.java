package com.example.boot3.jdbc.service;

import com.example.boot3.jdbc.StartJdbcApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author caimeng
 * @date 2024/6/3 16:59
 */
@ContextConfiguration(classes = StartJdbcApplication.class)
@ExtendWith(SpringExtension.class)
public class StartWithConfigTest {
    @Autowired
    private PubWithoutAnnotationService pubService;

    /**
     * 设置环境变量
     */
    @BeforeEach
    void init() {
        System.setProperty("annotation.enable", "true");
    }
    @Test
    public void editAllTest() {
        pubService.editAll();
    }
}
