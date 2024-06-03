package com.example.boot3.jdbc.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * 测试事务控制注解
 * @author caimeng
 * @date 2024/6/3 12:01
 */
@ContextConfiguration(locations = {
        // 注入spring bean
        "classpath:spring/spring-base.xml",
        // 通过tx命名空间进行AOP切面事务控制
        "classpath:spring/spring-transaction-aop.xml"
})
@ExtendWith(SpringExtension.class)
public class PubWithoutAnnotationServiceTest {
    @Autowired
    private PubWithoutAnnotationService pubService;

    /**
     * 测试事务切面控制
     * 测试失败：服务启动失败
     *      Cannot resolve reference to bean 'transactionPointCut' while setting bean property 'pointcut'
     */
    @Test
    public void editAllTest() {
        pubService.editAll();
    }
}
