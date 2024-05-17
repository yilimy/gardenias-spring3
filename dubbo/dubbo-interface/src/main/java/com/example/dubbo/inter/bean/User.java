package com.example.dubbo.inter.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author caimeng
 * @date 2024/5/16 14:13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    /**
     * 用户id
     */
    private Long id;
    /**
     * 用户名
     */
    private String name;
}
