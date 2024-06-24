package com.example.junit.mockito.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.junit.mockito.bean.po.JunitUserPO;
import com.example.junit.mockito.bean.vo.UserVO;

import java.util.List;

/**
 * @author caimeng
 * @date 2024/6/21 16:43
 */
public interface UserService extends IService<JunitUserPO> {

    /**
     * 通过ID查询用户信息
     * @param userId 用户ID
     * @return 用户信息
     */
    UserVO selectById(Long userId);

    /**
     * 新增一个用户及其用户特征
     * <p>
     *     方便测试 mockito 的api，所以参数分开传
     * @param username 用户名
     * @param phone 手机号
     * @param features 用户特征值
     */
    void add(String username, String phone, List<String> features);
}
