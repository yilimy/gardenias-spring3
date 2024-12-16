package com.ssm.mybatis.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author caimeng
 * @date 2024/12/16 10:11
 */
@Data
public class Account implements Serializable {
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
     * 数据库字段：INT
     * 设置为boolean，能更清晰的业务信息
     * 于是，需要转换器的支持
     */
    private boolean status;
}
