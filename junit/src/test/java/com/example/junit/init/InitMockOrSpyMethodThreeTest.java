package com.example.junit.init;

import com.example.junit.mockito.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

/**
 * 测试：第三种初始化方式
 * 使用 MockitoAnnotations.openMocks
 * 替代了 @ExtendWith
 * <p>
 *     迭代过程:
 *     {@link InitMockOrSpyMethodOneTest}
 *     {@link InitMockOrSpyMethodTwoTest}
 *     {@link InitMockOrSpyMethodThreeTest}
 * @author caimeng
 * @date 2024/6/28 15:43
 */
public class InitMockOrSpyMethodThreeTest {
    @Mock
    private UserService mockUserService;
    @Spy
    private UserService spyUserService;
    @BeforeEach
    public void init() {
        /*
         * 将传入的类中的mock相关的注解标记的属性，分别创建对应的对象
         */
        MockitoAnnotations.openMocks(this);
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
