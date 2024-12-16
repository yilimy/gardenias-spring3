package com.ssm.mybatis.vo;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author caimeng
 * @date 2024/12/16 11:45
 */
@Data
public class AccountAndDetails implements Serializable {
    /**
     * 账户ID
     */
    private String aid;
    /**
     * 账户姓名
     */
    private String name;
    /**
     * 账户锁定状态，0表示活跃，1表示锁定
     */
    private boolean status;
    /**
     * 账户详情
     * 一对一映射
     */
    @ToString.Exclude       // 循环引用的对象，不作为打印属性
    private Details details;
}
