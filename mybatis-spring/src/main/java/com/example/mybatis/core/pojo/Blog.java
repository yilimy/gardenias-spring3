package com.example.mybatis.core.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author caimeng
 * @date 2024/5/29 14:44
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Blog {
    private Integer id;
    private String userName;
    private String title;
    private String context;
    private Date updateTime;
}
