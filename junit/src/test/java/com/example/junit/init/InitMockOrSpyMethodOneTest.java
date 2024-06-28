package com.example.junit.init;

import com.example.junit.mockito.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * 测试：第一种初始化方式
 * (总共三种)
 * <a href="https://www.bilibili.com/video/BV1P14y1k7Hi/" />
 * 使用 @ExtendWith + @Mock or @Spy
 * <p>
 *     迭代过程:
 *     {@link InitMockOrSpyMethodOneTest}
 *     {@link InitMockOrSpyMethodTwoTest}
 *     {@link InitMockOrSpyMethodThreeTest}
 * @author caimeng
 * @date 2024/6/20 18:24
 */
@ExtendWith(MockitoExtension.class)
public class InitMockOrSpyMethodOneTest {
    /**
     * mock对象
     * 相当于对原对象做了代理，类似于Cglib，
     * mock使用的代理方式是 bytebuddy, 是通过修改字节码来实现的
     * mock的目的是返回作用对象的默认值
     */
    @Mock
    private UserService mockUserService;
    /**
     * 监视对象
     * spy is extends mock : spy对象是一种不同类型的mock
     * spy的目的是为了调用真实方法
     */
    @Spy
    private UserService spyUserService;

    @Test
    public void ifMock() {
        // 判断某对象是不是mock对象
        boolean ret1 = Mockito.mockingDetails(mockUserService).isMock();
        // mockUserService mock ? true
        System.out.println("mockUserService mock ? " + ret1);
        boolean ret2 = Mockito.mockingDetails(spyUserService).isMock();
        // spyUserService  mock ? true
        System.out.println("spyUserService  mock ? " + ret2);
        boolean ret3 = Mockito.mockingDetails(mockUserService).isSpy();
        // mockUserService spy  ? false
        System.out.println("mockUserService spy  ? " + ret3);
        boolean ret4 = Mockito.mockingDetails(spyUserService).isSpy();
        // spyUserService  spy  ? true
        System.out.println("spyUserService  spy  ? " + ret4);
    }
}
