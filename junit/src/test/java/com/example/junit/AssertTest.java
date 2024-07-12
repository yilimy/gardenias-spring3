package com.example.junit;

import org.assertj.core.api.Assertions;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.when;

/**
 * 测试：断言工具的使用
 * @author caimeng
 * @date 2024/7/12 18:12
 */
@ExtendWith(MockitoExtension.class)
public class AssertTest {
    @Mock
    private List<String> mockList;

    /**
     * 测试：使用 hamcrest 进行单元测试
     * 该工具比较老了
     */
    @SuppressWarnings("all")
    @Test
    public void hamcrestTest() {
        when(mockList.size()).thenReturn(999);
        /*
         * 测试使用 hamcrest 进行断言
         * 第一个参数为实际的值，第二个参数为匹配器
         */
        MatcherAssert.assertThat(mockList.size(), IsEqual.equalTo(999));
        /*
         * 测试使用 assertj 进行断言
         * 参数为实际的值
         */
        Assertions.assertThat(mockList.size()).isEqualTo(999);
        /*
         * 用的最多的是 Junit 5 的原生断言
         */
        org.junit.jupiter.api.Assertions.assertEquals(999, mockList.size());
        /*
         * Junit 4 的原生断言
         */
        org.junit.Assert.assertEquals(999, mockList.size());
    }
}
