package com.gardenia.web.lombok;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

/**
 * @author caimeng
 * @date 2025/1/21 17:54
 */
public class UserRecordTest {

    /**
     * 测试：record类的构造方法和toString()方法
     */
    @Test
    public void printTest() {
        // 只能通过全参数构造器构造
        UserRecord user = new UserRecord("Alice", 23);
        // 属性获取
        System.out.println("name=" + user.name());
        System.out.println("age=" + user.age());
        // toString() 方法测试
        // UserRecord[name=Alice, age=23]
        System.out.println(user);
        System.out.println("-".repeat(30));
        // 测试全参构造器
        UserRecord user2 = new UserRecord("Alice", 23, "12306@yahoo.com");
        // UserRecord[name=Alice, age=23, email=12306@yahoo.com]
        System.out.println(user2);
    }

    /**
     * 测试：record类的方法调用
     */
    @Test
    public void methodTest() {
        UserRecord user = new UserRecord("Alice", 23, "12306@yahoo.com");
        // 测试普通方法调用
        String contact = user.contact();
        // contact=姓名: Alice, 年龄:23
        System.out.println("contact=" + contact);
        System.out.println("-".repeat(30));
        // 测试静态方法调用
        String emailUpper = UserRecord.emailUpperCase("12306@163.com");
        // emailUpper=12306@163.COM
        System.out.println("emailUpper=" + emailUpper);
    }

    /**
     * 测试：对 record 类使用json进行序列化
     */
    @SneakyThrows
    @Test
    public void jsonTest() {
        record Person(String name, Integer age){}
        Person person = new Person("Alice", 23);
        // 序列化 record 类使用 jackson 需要版本2.12+
        ObjectMapper objectMapper = new ObjectMapper();
        String jacksonStr = objectMapper.writeValueAsString(person);
        // jacksonStr = {"name":"Alice","age":23}
        System.out.println("jacksonStr = " + jacksonStr);
        System.out.println("-".repeat(30));
        Person personFromJackson = objectMapper.readValue(jacksonStr, Person.class);
        // personFromJackson = Person[name=Alice, age=23]
        System.out.println("personFromJackson = " + personFromJackson);
        System.out.println("-".repeat(30));
        /*
         * 序列化 record 类使用 fastjson 需要版本 2.03+
         * 事实证明，版本 2.0.9 不能很好的实现序列化，改到版 2.0.54 可以正常序列化
         * <p>
         *      源码分析:
         *      1. 创建 ObjectWriter 时判断了 record 类型
         *          com.alibaba.fastjson2.writer.ObjectWriterCreator#createObjectWriter(java.lang.Class, long, com.alibaba.fastjson2.writer.ObjectWriterProvider)
         *          boolean record = BeanUtils.isRecord(objectClass);
         *      2. 读取属性列表 fieldWriterMap 时判断了是否 record 类
         *          在getter中拿到 record 方法的属性获取方法， 比如: age()
         *              com.alibaba.fastjson2.util.BeanUtils#getters(java.lang.Class, java.lang.Class, boolean, java.util.function.Consumer<java.lang.reflect.Method>)
         *              recordFieldNames = getRecordFieldNames(objectClass);
         *          再将 record 属性映射到 fieldWriterMap 中
         *              FieldWriter origin = fieldWriterMap.putIfAbsent(fieldWriter.fieldName, fieldWriter);
         */
        String fastStr = JSON.toJSONString(person);
        // fastStr = {"age":23,"name":"Alice"}
        System.out.println("fastStr = " + fastStr);
        System.out.println("-".repeat(30));
        Object personFromFast = JSON.parseObject(fastStr, Person.class);
        // personFromFast = Person[name=Alice, age=23]
        System.out.println("personFromFast = " + personFromFast);
        System.out.println("-".repeat(30));
    }
}
