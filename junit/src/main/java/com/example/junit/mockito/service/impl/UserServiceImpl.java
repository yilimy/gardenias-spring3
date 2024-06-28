package com.example.junit.mockito.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.junit.mockito.bean.po.JunitUserFeaturePO;
import com.example.junit.mockito.bean.po.JunitUserPO;
import com.example.junit.mockito.bean.req.UserUpdateReq;
import com.example.junit.mockito.bean.vo.UserVO;
import com.example.junit.mockito.mapper.JunitUserMapper;
import com.example.junit.mockito.service.UserFeatureService;
import com.example.junit.mockito.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author caimeng
 * @date 2024/6/21 16:50
 */
@Service
public class UserServiceImpl extends ServiceImpl<JunitUserMapper, JunitUserPO> implements UserService {
    @Resource
    private UserFeatureService userFeatureService;
    @Override
    public UserVO selectById(Long userId) {
        JunitUserPO userPO = getById(userId);
        if (Objects.isNull(userPO)) {
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtil.copyProperties(userPO, userVO);
        List<JunitUserFeaturePO> junitUserFeaturePOS = userFeatureService.selectByUserId(userId);
        if (ObjectUtils.isEmpty(junitUserFeaturePOS)) {
            return userVO;
        }
        List<String> features = junitUserFeaturePOS.stream().map(JunitUserFeaturePO::getFeatureValue).collect(Collectors.toList());
        userVO.setFeatureValue(features);
        return userVO;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(String username, String phone, List<String> features) {
        JunitUserPO userPO = new JunitUserPO();
        userPO.setUsername(username);
        userPO.setPhone(phone);
        save(userPO);
        // 保存特征值
        List<JunitUserFeaturePO> featurePOList = features.stream()
                .map(feature -> JunitUserFeaturePO.builder()
                        .featureValue(feature)
                        .userId(userPO.getId())
                        .build())
                .collect(Collectors.toList());
        userFeatureService.saveBatch(featurePOList);
    }

    @Override
    public int modifyById(UserUpdateReq userUpdateReq) {
        JunitUserPO userPO = new JunitUserPO();
        userPO.setId(userPO.getId());
        userPO.setUsername(userUpdateReq.getUsername());
        userPO.setPhone(userUpdateReq.getPhone());
        boolean b = updateById(userPO);
        return b ? 1 : 0;
    }
}
