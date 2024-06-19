package org.example.foundation;

import org.example.foundation.beans.AppConfig;
import org.example.foundation.beans.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author caimeng
 * @date 2024/6/17 17:35
 */
public class FoundationTest {

    @Test
    public void firstInvoke() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        UserService userService = (UserService) context.getBean("userService");
        // org.example.foundation.beans.service.OrderService@5b67bb7e
        userService.test();
    }
}
