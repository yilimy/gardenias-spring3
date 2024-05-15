package com.example.dubbo.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.example.dubbo.inter.bean.UserAddress;
import com.example.dubbo.inter.service.UserService;

import java.util.Arrays;
import java.util.List;

/**
 * @author caimeng
 * @date 2024/4/29 10:46
 */
@Service
public class UserServiceImpl implements UserService {
    @Override
    public List<UserAddress> getUserAddressList(String userId) {
        UserAddress address1 = new UserAddress(1, "贫瘠之地", "ID0023", "艾格.雷霆", "15188767653", "Y");
        UserAddress address2 = new UserAddress(2, "永望镇", "ID3058", "格雷.瑞恩", "13337648832", "N");
        UserAddress address3 = new UserAddress(3, "贝洛伯格", userId, "银狼", "139***7624", "N");
        return Arrays.asList(address1, address2, address3);
    }
}
