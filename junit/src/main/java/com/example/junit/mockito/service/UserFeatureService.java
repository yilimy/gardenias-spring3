package com.example.junit.mockito.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.junit.mockito.bean.po.JunitUserFeaturePO;

import java.util.List;

/**
 * @author caimeng
 * @date 2024/6/21 16:47
 */
public interface UserFeatureService extends IService<JunitUserFeaturePO> {

    /**
     * 通过用户ID查询用户特征值
     * @param userId 用户ID
     * @return 用户特征值
     */
    List<JunitUserFeaturePO> selectByUserId(Long userId);
}
