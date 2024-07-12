package com.example.junit;

import com.example.junit.mockito.service.UserFeatureService;
import com.example.junit.mockito.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * 测试： @InjectMocks 注解使用
 * @author caimeng
 * @date 2024/7/12 16:43
 */
@ExtendWith(MockitoExtension.class)
public class InjectMocksTest {
    /**
     * 被 @InjectMocks 标注的类不能是接口，因为Mockito会创建该注解标注的对象的实例
     * 创建的是一个没有经过mockito处理的普通对象,
     * 因此，常配合@spy注解，使其变为默认调用的真实方法的mock对象。
     * 如果 @InjectMocks 声明的对象，需要用到Mock/Spy对象，mockito会自动使用当前类里面的 Mock|Spy 成员，进行按类或者名字的注入
     * 注入的顺序：构造器注入，setter注入，字段反射注入
     * <p>
     *     实际测试
     *          跟 @Resource 没关系: 删除该注解也能注入
     *          跟字段名也没关系: 当前@mock的 userFeatureService -> userFeatureService1 后也能注入
     *          可能是跟据属性类型注入的
     */
    @InjectMocks
    @Spy
    private UserServiceImpl userServiceImpl;
    /**
     * 注解 @Mock 的对象将会自动注入到含有 @InjectMocks 的注解的对象中（如果该对象含有 @Mock 注解对象的属性的话）
     */
    @Mock
    private UserFeatureService userFeatureService1;

    @Test
    public void test1() {
        /*
         * 不加@Spy时，调用的结果: com.example.junit.mockito.service.impl.UserServiceImpl@376a312c
         * 添加@Spy时，调用的结果: com.example.junit.mockito.service.impl.UserServiceImpl$MockitoMock$zYIGY9SC@11ce2e22
         */
        System.out.println(userServiceImpl);
    }

    /**
     * 测试： 对象调用
     */
    @Test
    public void invokeTest() {
        int number = userServiceImpl.getNumber();
        System.out.println(number);
    }

}
