package com.example.dubbo.inter.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author caimeng
 * @date 2024/4/29 10:40
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
//@Accessors(chain = true)
public class UserAddress implements Serializable {
    /**
     * id
     */
    private Integer id;
    /**
     * 用户地址
     */
    private String userAddress;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 收货人
     */
    private String consignee;
    /**
     * 电话号码
     */
    private String phoneNum;
    /**
     * 是否为默认地址：
     * Y - 是
     * N - 否
     */
    private String isDefault;
}
