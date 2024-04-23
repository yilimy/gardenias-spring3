package com.example.dubbo.inter;

/**
 * 消费端和生产端的服务，都是围绕interface中的接口展开的
 * @author caimeng
 * @date 2024/4/18 14:14
 */
public interface DemoService {

    String sayHello(String name);
}
