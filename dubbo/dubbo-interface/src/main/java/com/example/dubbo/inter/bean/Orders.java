package com.example.dubbo.inter.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 订单实体类
 * @author caimeng
 * @date 2024/5/15 15:14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Orders implements Serializable {
    /**
     * 订单id
     */
    private Long id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 订单总价
     */
    private Double priceTotal;
    /**
     * 收货人手机号
     */
    private String mobile;
    /**
     * 收货人地址
     */
    private String address;
    /**
     * 支付类型 1- 微信; 2 - 支付宝
     */
    private Integer payMethod;
}
