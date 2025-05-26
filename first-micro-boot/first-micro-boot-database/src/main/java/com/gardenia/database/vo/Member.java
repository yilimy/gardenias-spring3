package com.gardenia.database.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author caimeng
 * @date 2025/5/21 16:31
 */
@Data
@TableName("member3") // 定义该VO类对应的数据表名称
public class Member {
    @TableId    // 配置主键
    private String mid;
    private String name;
    private Integer age;
    private Double salary;
    private Date birthday;
    private String content;
    @TableLogic // 配置逻辑删除字段
    private Integer del;
}
