package com.example.boot3.aop.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author caimeng
 * @date 2024/5/6 17:28
 */
@Data
@Accessors(chain = true)
public class Dept {
    private Long no;
    private String name;
    private String loc;
}
