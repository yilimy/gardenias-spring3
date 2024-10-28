package com.ssm.mybatis.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 实体类
 * @TableName book
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName(value ="book")
@Data
public class Book implements Serializable {
    /**
     * 图书ID
     */
    @TableId(type = IdType.AUTO)
    private Long bid;

    /**
     * 图书名称
     */
    private String title;

    /**
     * 图书作者
     */
    private String author;

    /**
     * 图书价格
     */
    private Double price;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}