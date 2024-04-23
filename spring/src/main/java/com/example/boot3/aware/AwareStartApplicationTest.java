package com.example.boot3.aware;

import com.example.boot3.aware.impl.MyDataBase;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author caimeng
 * @date 2024/4/16 14:57
 */
@Slf4j
public class AwareStartApplicationTest {

    @Test
    public void autoAwareTest() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.scan("com.example.boot3.aware");
        context.refresh();
        MyDataBase dataBase = context.getBean(MyDataBase.class);
        log.info("config = {}", dataBase.getConfig());
    }
}
