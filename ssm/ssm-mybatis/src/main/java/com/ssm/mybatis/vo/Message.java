package com.ssm.mybatis.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author caimeng
 * @date 2024/12/17 11:31
 */
@Data
public class Message implements Serializable {
    /**
     * 主键
     */
    private Long mid;
    /**
     * 消息标题
     */
    private String title;
    /**
     * 发送者
     */
    private String sender;
    /**
     * 消息体
     */
    private String content;
}
