package com.example.dubbo.provider.service;

import com.example.dubbo.provider.pojo.UserAddress;

import java.util.List;

/**
 * 用户服务
 * @author caimeng
 * @date 2024/4/29 10:44
 */
public interface UserService {

    /**
     * 通过用户ID获取用户地址
     * @param userId 用户ID
     * @return 用户地址
     */
    List<UserAddress> getUserAddressList(String userId);
}
