package org.example.mock.service.beans;

import org.example.mock.spring.IComponent;

/**
 * 测试切面的bean
 * 简单Cglib代理
 * @author caimeng
 * @date 2024/6/14 15:54
 */
@IComponent
public class AopSimpleService {

    public void test() {
        System.out.println(AopSimpleService.class.getSimpleName() + " test ...");
    }
}
