package com.ssm.mybatis.vo;

import lombok.Data;
import lombok.ToString;

/**
 * 账户详情
 * @author caimeng
 * @date 2024/12/16 11:46
 */
@Data
public class Details {
    /**
     * 账户ID
     */
    private String aid;
    /**
     * 人民币存款总额
     */
    private Double rmb;
    /**
     * 美元存款总额
     */
    private Double dollar;
    /**
     * 欧元存款总额
     */
    private Double euro;
    /**
     * 一对一映射
     */
    @ToString.Exclude       // 循环引用的对象，不作为打印属性
    private AccountAndDetails accountAndDetails;
}
