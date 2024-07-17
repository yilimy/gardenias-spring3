package com.example.junit.mockito.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.example.junit.mockito.bean.po.JunitUserFeaturePO;
import com.example.junit.mockito.bean.po.JunitUserPO;
import com.example.junit.mockito.bean.vo.UserVO;
import com.example.junit.mockito.mapper.JunitUserMapper;
import com.example.junit.mockito.service.UserFeatureService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.doReturn;

/**
 * 实战
 * @author caimeng
 * @date 2024/7/12 18:28
 */
@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    /**
     * 被测试的类一般都要标记这两个注解
     */
    @InjectMocks
    @Spy
    private UserServiceImpl userService;
    @Mock
    private JunitUserMapper junitUserMapper;
    @Mock
    private UserFeatureService userFeatureService;

    /**
     * 该测试执行到 userPO = null 就中断了
     */
    @Test
    public void selectById1Test() {
        /*
         * 1. 调用该方法 (userServiceImpl.selectById) 时，没有Mybatis提供的Mapper代理对象，因此需要设置(指mock一下)
         * 2. doReturn 一下
         */
        doReturn(junitUserMapper).when(userService).getBaseMapper();
        UserVO userVO = userService.selectById(1L);
        Assertions.assertNull(userVO);
    }

    @Test
    public void selectById2Test() {
        Long userId = 1L;
        JunitUserPO userPO = JunitUserPO.builder().id(userId).username("小明").phone("18618523654").build();
        /*
         * 指定 JunitUserPO userPO = getById(userId) 返回的结果为自定义的对象 userPO
         * 因为mock了该方法(userService.getById(userId))，所以 selectById1Test 中的
         *      doReturn(junitUserMapper).when(userService).getBaseMapper();
         * 被认为是多余的插桩，可以去掉了。(不去掉会报错)
         */
        doReturn(userPO).when(userService).getById(userId);
        List<JunitUserFeaturePO> junitUserFeaturePOS = Stream.generate(() -> RandomUtil.randomString(8)).limit(5).map(str -> {
            JunitUserFeaturePO junitUserFeaturePO = new JunitUserFeaturePO();
            junitUserFeaturePO.setId(RandomUtil.randomLong());
            junitUserFeaturePO.setUserId(userId);
            junitUserFeaturePO.setFeatureValue(str);
            return junitUserFeaturePO;
        }).collect(Collectors.toList());
        doReturn(junitUserFeaturePOS).when(userFeatureService).selectByUserId(userId);
        // 最终测试的目标方法
        UserVO userVO = userService.selectById(1L);
        Assertions.assertNotNull(userVO);
        System.out.println(userVO);
    }
}
