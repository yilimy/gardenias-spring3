package com.example.boot3.jdbc.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * 测试事务控制注解
 * <p>
 *     spring-base.xml 扫描注入spring bean
 *     spring-transaction.xml 开启事务
 * @author caimeng
 * @date 2024/6/3 12:01
 */
@ContextConfiguration(locations = "classpath:spring/spring-*.xml")
@ExtendWith(SpringExtension.class)
public class BookServiceTest {
    @Autowired
    private BookService bookService;

    /**
     * 事务中出现了错误，删除代码不执行
     */
    @Test
    public void removeTest() {
        bookService.remove();
    }

    /**
     * 开启事务： 删除一条数据，新增一条数据
     */
    @Test
    public void removeSuccessTest() {
        bookService.removeSuccess();
    }
}
