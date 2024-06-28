package com.example.junit.param;

import com.example.junit.mockito.bean.req.UserUpdateReq;
import com.example.junit.mockito.bean.vo.UserVO;
import com.example.junit.mockito.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

/**
 * 测试参数匹配
 * <a href="https://www.bilibili.com/video/BV1P14y1k7Hi?p=4" />
 * <p>
 *     参数匹配：通过方法签名（参数）来指定哪些方法调用需要被处理（插桩 | 验证）
 * @author caimeng
 * @date 2024/6/28 17:13
 */
@ExtendWith(MockitoExtension.class)
public class ParamMatcherTest {
    @Mock
    private UserService mockUserService;

    /**
     * 对于mock对象不会真实调用真实方法，直接返回默认值
     * 默认值：对应类型的默认值，0，null，空集合
     */
    @Test
    public void test1() {
        UserVO userVO = mockUserService.selectById(1L);
        // userVO = null
        System.out.println("userVO = " + userVO);
        UserUpdateReq userUpdateReq = new UserUpdateReq();
        int i = mockUserService.modifyById(userUpdateReq);
        // i = 0
        System.out.println("i = " + i);
    }

    /**
     * 插桩测试
     * <p>
     *     插桩的两种方式
     *     when(obj.someMethod()).thenXxx()     用于mock对象
     *     doXxx().when(obj).someMethod()       obj可以是 mock/spy 对象
     */
    @Test
    public void insertingTest() {
        UserUpdateReq userUpdateReq = new UserUpdateReq();
        userUpdateReq.setId(1L);
        userUpdateReq.setPhone("18829276553");
        /*
         * 指定参数为 userUpdateReq 时，mockUserService 调用 modifyById 方法返回 99
         * userUpdateReq 变化将导致 doReturn 不会被触发，即mock的原方法返回默认值
         */
        Mockito
                // 插桩结果值
                .doReturn(99)
                // 待测试对象
                .when(mockUserService)
                // 待测试对象的方法调用
                .modifyById(userUpdateReq);
        int i = mockUserService.modifyById(userUpdateReq);
        // i = 99
        System.out.println("i = " + i);
        // 修改参数，同对象调用同方法
        UserUpdateReq userUpdateReq2 = UserUpdateReq.builder().id(2L).phone("18628737733").build();
        int j = mockUserService.modifyById(userUpdateReq2);
        // j = 0
        System.out.println("j = " + j);
    }

    /**
     * 插桩测试：拦截 UserUpdateReq 类型的任意对象
     * ArgumentMatchers.any 拦截
     */
    @Test
    public void insertingAnyTest() {
        Mockito.doReturn(99).when(mockUserService)
                // 待测试对象的方法调用
                .modifyById(ArgumentMatchers.any(UserUpdateReq.class));
        UserUpdateReq userUpdateReq1 = UserUpdateReq.builder().id(1L).phone("18829276553").build();
        UserUpdateReq userUpdateReq2 = UserUpdateReq.builder().id(2L).phone("18628737733").build();
        int ret1 = mockUserService.modifyById(userUpdateReq1);
        int ret2 = mockUserService.modifyById(userUpdateReq2);
        // ret1 = 99; ret2 = 99
        System.out.println("ret1 = " + ret1 + "; ret2 = " + ret2);
    }

    /**
     * 调用次数测试
     */
    @Test
    public void timesTest() {
        mockUserService.add("新增测试", "18892765374", List.of("A", "B", "C"));
        /*
         * 校验该方法该参数 1 次
         * 通过
         */
        Mockito.verify(mockUserService, Mockito.times(1))
                .add("新增测试", "18892765374", List.of("A", "B", "C"));
        /*
         * 失败：
         *      Wanted 2 times: xxx
         *      But was 1 time: xxx
         */
        Mockito.verify(mockUserService, Mockito.times(2))
                .add("新增测试", "18892765374", List.of("A", "B", "C"));
        /*
         * 变更了参数 $1
         * Wanted but not invoked:
         * mockUserService.add(
         *     "新增测试2",
         *     "18892765374",
         *     [A, B, C]
         * );
         * However, there was exactly 1 interaction with this mock:
         * mockUserService.add(
         *     "新增测试",
         *     "18892765374",
         *     [A, B, C]
         * );
         */
        Mockito.verify(mockUserService, Mockito.times(1))
                .add("新增测试2", "18892765374", List.of("A", "B", "C"));
    }

    /**
     * 测试：任意参数类型
     * 对照组 {@link ParamMatcherTest#timesTest()}
     */
    @Test
    public void argumentMatchersTest() {
        mockUserService.add("新增测试", "18892765374", List.of("A", "B", "C"));
        // 当使用匹配器的时候，所有的参数，要么都用，要么都不用
        Mockito.verify(mockUserService, Mockito.times(1))
                .add(ArgumentMatchers.anyString(), ArgumentMatchers.anyString(), ArgumentMatchers.anyList());

    }
}
