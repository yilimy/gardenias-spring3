package org.example.foundation.beans.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 用户服务组件
 * @author caimeng
 * @date 2024/6/17 17:24
 */
@Component
public class UserService {
    @Autowired
    private OrderService orderService;

    public void test() {
        System.out.println(orderService);
    }
}
