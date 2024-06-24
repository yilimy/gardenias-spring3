package com.example.junit.mockito.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.junit.mockito.bean.po.JunitUserFeaturePO;
import com.example.junit.mockito.mapper.JunitUserFeatureMapper;
import com.example.junit.mockito.service.UserFeatureService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author caimeng
 * @date 2024/6/21 16:53
 */
@Service
public class UserFeatureServiceImpl extends ServiceImpl<JunitUserFeatureMapper, JunitUserFeaturePO> implements UserFeatureService {
    @Override
    public List<JunitUserFeaturePO> selectByUserId(Long userId) {
        if (Objects.nonNull(userId)) {
            return list(new QueryWrapper<JunitUserFeaturePO>().lambda()
                    .eq(JunitUserFeaturePO::getUserId, userId));
        }
        return null;
    }
}
