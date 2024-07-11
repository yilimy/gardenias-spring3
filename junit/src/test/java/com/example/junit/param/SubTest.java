package com.example.junit.param;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
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
     * 两种插桩的方式：
     *      - when(obj.someMethod()).thenXxx(), 其中obj可以是mock对象
     *      - doXxx().when(obj).someMethod(), 其中obj可以是mock/spy对象
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
}
