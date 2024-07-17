package com.example.junit.mockito.bean.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
* 用户表
*/
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@TableName("junit_user")
public class JunitUserPO implements Serializable {
    /**
    * 主键
    */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
    * 用户名称
    */
    private String username;
    /**
    * 电话
    */
    private String phone;
}
