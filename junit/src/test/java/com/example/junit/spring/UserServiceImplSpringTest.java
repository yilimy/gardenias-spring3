package com.example.junit.spring;

import cn.hutool.core.util.RandomUtil;
import com.example.junit.JunitApplication;
import com.example.junit.mockito.bean.po.JunitUserFeaturePO;
import com.example.junit.mockito.bean.po.JunitUserPO;
import com.example.junit.mockito.bean.vo.UserVO;
import com.example.junit.mockito.mapper.JunitUserMapper;
import com.example.junit.mockito.service.UserFeatureService;
import com.example.junit.mockito.service.impl.UserServiceImpl;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.doReturn;

/**
 * Junit 在spring中的测试
 * 在这里就不用 {@link org.junit.jupiter.api.extension.ExtendWith}, 使用 SpringBootTest 替代
 * 如果不想配置 DataSource 的bean，也可以mock进行注入(@SpyBean)
 * @author caimeng
 * @date 2024/7/16 17:24
 */
@SpringBootTest(classes = JunitApplication.class)
public class UserServiceImplSpringTest {

    /**
     * Spy不能配合Resource进行属性注入，会报错
     *      Argument passed to when() is not a mock!
     */
    @Resource
    @SpyBean
    private UserServiceImpl userService;
    @MockBean
    private JunitUserMapper junitUserMapper;
    @MockBean
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
