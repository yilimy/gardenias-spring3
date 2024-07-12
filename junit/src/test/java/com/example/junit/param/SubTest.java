package com.example.junit.param;

import com.example.junit.mockito.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.*;

/**
 * 5. 指定返回值
 * <a href="https://www.bilibili.com/video/BV1P14y1k7Hi?p=5" />
 * @author caimeng
 * @date 2024/7/11 18:29
 */
@ExtendWith(MockitoExtension.class)
public class SubTest {
    @Mock
    private List<String> mockList;
    /**
     * mock UserService 的实现类
     */
    @Mock
    private UserServiceImpl mockUserServiceImpl;
    /**
     * spy UserService 的实现类
     */
    @Spy
    private UserServiceImpl spyUserServiceImpl;

    /**
     * 两种插桩的方式：
     *      - when(obj.someMethod()).thenXxx(), 其中obj可以是mock对象
     *      - doXxx().when(obj).someMethod(), 其中obj可以是mock/spy对象, 或者是无返回值的情况
     */
    @Test
    public void returnTest() {
        // 方法一
        doReturn("zero").when(mockList).get(0);
        // 如果返回值不相相等，单元测试会失败
        Assertions.assertEquals("zero", mockList.get(0));

        // 方法二
        when(mockList.get(1)).thenReturn("one");
        Assertions.assertEquals("one", mockList.get(1));
    }

    /**
     * 测试：没有返回值的方法
     */
    @Test
    public void voidTest() {
        /*
         * void 类型的方法，没法使用 when(obj.someMethod()).thenXxx()，因为没有返回值
         * 表示调用 mockList.clear() 的时候什么也不做
         */
        doNothing().when(mockList).clear();
        mockList.clear();
        // 验证 mockList.clear() 方法调用了1次
        verify(mockList, times(1)).clear();
    }

    /**
     * 插桩的两种方式 (mock | spy)
     * <p>
     *     mock对象不会调用的真实方法，会直接返回mock的返回值；
     *     spy 对象会调用真实的方法，也会返回mock的返回值。
     * <p>
     *     spy对象在没有插桩时是调用真实的方法的，写在when中会导致先执行一次原方法，达不到mock的目的，
     *     需要使用 doXxx().when(obj).someMethod() 的格式
     */
    @Test
    public void twoDiffTest() {
        when(mockUserServiceImpl.getNumber()).thenReturn(99);
        /*
         * 不会调用真实的方法，会直接返回99。
         * 因此真实方法中的打印也不会在日志体现。
         */
        int number = mockUserServiceImpl.getNumber();
        System.out.println("number = " + number);
        // 换成spy调用
        System.out.println("-".repeat(20));
        when(spyUserServiceImpl.getNumber()).thenReturn(99);
        number = spyUserServiceImpl.getNumber();
        /*
         * 发现打印了方法中的日志输出，说明方法被调用了。
         * UserServiceImpl$MockitoMock$pNKUqshy
         * number = 99
         */
        System.out.println("number = " + number);
        // 换成spy-do调用
        System.out.println("-".repeat(20));
        doReturn(1000).when(spyUserServiceImpl).getNumber();
        number = spyUserServiceImpl.getNumber();
        /*
         * 使用spy-do的时候，不再调用真实方法。
         * number = 1000
         */
        System.out.println("number = " + number);
    }

    /**
     * 测试：让指定的插桩抛出异常
     */
    @SuppressWarnings("all")
    @Test
    public void exceptionTest() {
        /*
         * 方式一
         * 假定：mockList调用get方法，入参为任意整数时，都会抛出RuntimeException异常
         */
        doThrow(RuntimeException.class).when(mockList).get(anyInt());
        try {
            mockList.get(4);
            System.out.println("failed " + "-".repeat(13));
            // 走到这一行说明插桩失败了
            Assertions.fail();
        } catch (Exception e) {
            // 断言表达式为真
            Assertions.assertTrue(e instanceof RuntimeException);
        }

    }

    /**
     * 测试：让指定的插桩抛出异常
     * 通过when的方式执行
     */
    @SuppressWarnings("all")
    @Test
    public void exception2Test() {
        // 方法二
        when(mockList.get(anyInt())).thenThrow(RuntimeException.class);
        try {
            mockList.get(4);
            // 实际上走不到这一步
            System.out.println("failed " + "-".repeat(13));
            Assertions.fail();
        } catch (Exception e) {
            Assertions.assertTrue(e instanceof RuntimeException);
        }
    }

    /**
     * 多次插桩
     */
    @Test
    public void multiTest() {
        when(mockList.size()).thenReturn(1).thenReturn(2).thenReturn(3);
        // 第一次调用返回1
        Assertions.assertEquals(1, mockList.size());
        // 第二次调用返回2
        Assertions.assertEquals(2, mockList.size());
        // 第三次调用返回3
        Assertions.assertEquals(3, mockList.size());
        // 三次及以上，返回3
        Assertions.assertEquals(3, mockList.size());
    }

    /**
     * 多次插桩的另一种写法
     * @see this#multiTest()
     */
    @Test
    public void multi2Test() {
        // 另一种写法
        when(mockList.size()).thenReturn(1, 2, 3);
        Assertions.assertEquals(1, mockList.size());
        Assertions.assertEquals(2, mockList.size());
        Assertions.assertEquals(3, mockList.size());
        Assertions.assertEquals(3, mockList.size());
    }

    /**
     * 测试：实现指定插桩逻辑
     */
    @Test
    public void answerTest() {
        when(mockList.get(anyInt())).thenAnswer(invocation -> {
            // 获取方法参数种第一个参数
            Integer argument = invocation.getArgument(0, Integer.class);
            // 自定义返回结果
            return String.valueOf(argument * 100);
        });
        String result = mockList.get(3);
        Assertions.assertEquals("300", result );
    }

    /**
     * 测试：执行真正的原来的方法
     */
    @Test
    public void actuallyTest() {
        // 对mock对象插桩，让他执行原始方法
        when(mockUserServiceImpl.getNumber()).thenCallRealMethod();
        int number = mockUserServiceImpl.getNumber();
        // UserServiceImpl$MockitoMock$OlwSgYux
        Assertions.assertEquals(0, number);

        System.out.println("-".repeat(20));

        /*
         * spy也会调用原方法
         * 如果不想让它调用原方法，需要单独为它插桩
         */
        number = spyUserServiceImpl.getNumber();
        // UserServiceImpl$MockitoMock$OlwSgYux
        Assertions.assertEquals(0, number);

        System.out.println("-".repeat(20));

        // 不让spy调用真实方法
        doReturn(1000).when(spyUserServiceImpl).getNumber();
        number = spyUserServiceImpl.getNumber();
        Assertions.assertEquals(1000, number);
    }

    /**
     * 测试：验证方法调用
     */
    @Test
    public void verifyTest() {
        // 方法调用
        mockList.add("one");
        // true，调用mock对象的写操作是无效的
        Assertions.assertEquals(0, mockList.size());
        // 方法调用
        mockList.clear();
        // 验证调用过一次add方法，并且参数必须是one
        verify(mockList)
                /*
                 * 要指定验证的方法和参数
                 * 注意：这里不是调用，是验证。也不会会调用，产生调用效果。
                 */
                .add("one");
        // 等价于上面的 verify(mockList)
        verify(mockList, times(1)).clear();
        // 验证没有调用的两种方式
        verify(mockList, times(0)).get(1);
        verify(mockList, never()).get(1);
        // 校验最少或者最多调用了多少次
        verify(mockList, atLeast(1)).clear();
        verify(mockList, atMost(3)).clear();
    }

}
