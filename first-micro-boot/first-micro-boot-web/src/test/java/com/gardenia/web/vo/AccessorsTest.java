package com.gardenia.web.vo;

import org.junit.jupiter.api.Test;

/**
 * @author caimeng
 * @date 2025/1/22 15:12
 */
public class AccessorsTest {
    /**
     * 测试：访问器模式，fluent
     */
    @Test
    public void fluentTest() {
        User user = new User();
        user.name("Alice").age(23);
        // user = User(name=Alice, age=23)
        System.out.println("user = " + user);
        System.out.println("-".repeat(30));
        String name = user.name();
        // name = Alice
        System.out.println("name = " + name);
        System.out.println("-".repeat(30));
    }

    /**
     * 测试：访问器模式，chain
     */
    @Test
    public void chainTest() {
        Person person = new Person().setName("Bob").setAge(18);
        // person = Person(name=Bob, age=18)
        System.out.println("person = " + person);
    }
}
