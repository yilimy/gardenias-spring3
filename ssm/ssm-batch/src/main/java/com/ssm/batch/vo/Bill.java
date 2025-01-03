package com.ssm.batch.vo;

import lombok.Data;

import java.util.Date;

/**
 * 对应资源: /data/bill.txt
 * @author caimeng
 * @date 2025/1/3 17:15
 */
@Data
public class Bill {
    /**
     * 账户ID
     */
    private Long id;
    /**
     * 账户名称
     */
    private String name;
    /**
     * 金额
     */
    private Double amount;
    /**
     * 交易时间
     */
    private Date transaction;
    /**
     * 交易地址
     */
    private String location;
}
