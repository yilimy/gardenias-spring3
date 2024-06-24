package com.example.junit.mockito.bean.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
* 用户表
*/
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
