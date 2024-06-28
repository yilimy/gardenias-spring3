package com.example.junit.init;

import com.example.junit.mockito.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/**
 * 测试：第二种初始化方式
 * 使用静态方法 @BeforeEach
 * <p>
 *     迭代过程:
 *     {@link InitMockOrSpyMethodOneTest}
 *     {@link InitMockOrSpyMethodTwoTest}
 *     {@link InitMockOrSpyMethodThreeTest}
 * @author caimeng
 * @date 2024/6/28 15:40
 */
public class InitMockOrSpyMethodTwoTest {
    private UserService mockUserService;
    private UserService spyUserService;

    @BeforeEach
    public void init() {
        mockUserService = Mockito.mock(UserService.class);
        spyUserService = Mockito.spy(UserService.class);
    }

    @Test
    public void ifMock() {
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
