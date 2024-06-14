package org.example.mock.service.beans;

import org.example.mock.spring.IAutowired;
import org.example.mock.spring.IComponent;

/**
 * 依赖注入的bean
 * @author caimeng
 * @date 2024/6/13 14:32
 */
@IComponent
public class InjectService {
    @IAutowired
    private UserService userService;

    public void test() {
        System.out.println("userService injected : " + userService);
    }
}
