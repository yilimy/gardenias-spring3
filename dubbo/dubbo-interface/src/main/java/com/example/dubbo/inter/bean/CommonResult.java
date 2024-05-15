package com.example.dubbo.inter.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 统一的返回结果集
 * @author caimeng
 * @date 2024/5/15 15:21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CommonResult <T> implements Serializable {
    /**
     * 返回结果编码
     */
    private Integer code;
    /**
     * 返回结果描述
     */
    private String message;
    /**
     * 数据实体
     */
    private T data;
}
