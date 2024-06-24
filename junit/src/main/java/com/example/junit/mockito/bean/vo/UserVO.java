package com.example.junit.mockito.bean.vo;

import lombok.Data;

import java.util.List;

/**
 * @author caimeng
 * @date 2024/6/21 16:44
 */
@Data
public class UserVO {
    /**
     * 用户名称
     */
    private String username;
    /**
     * 电话
     */
    private String phone;
    /**
     * 特征值
     */
    private List<String> featureValue;
}
