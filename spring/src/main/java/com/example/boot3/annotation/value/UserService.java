package com.example.boot3.annotation.value;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author caimeng
 * @date 2024/7/19 9:47
 */
@Component
public class UserService {
    @Value("123456")
    private String version;
    /**
     * "$"表示占位符
     * 从环境变量中取出值
     */
    @Value("${version}")
    private String placeholder;
    /**
     * "#"表示解析SpEL表达式
     * 此时返回的是一个名字为 version 的 bean
     */
    @Value("#{version}")
    private String expression;
    /**
     * 通过"#"注入一个bean对象
     */
    @Value("#{user}")
    private User expressionUser;
    @SamePort
    private String port1;
    @SamePort
    private String port2;

    public void test() {
        System.out.println(version);
    }

    public void test2() {
        System.out.println(placeholder);
    }

    public void test3() {
        System.out.println(expression);
    }

    public void test4() {
        System.out.println(expressionUser);
    }

    public void test5() {
        System.out.printf("port1=%s, port2=%s", port1, port2);
    }

}
