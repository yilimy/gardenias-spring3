package com.ssm.mybatis.plus.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author caimeng
 * @date 2024/12/23 10:01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName  // 此时表名与类名一致
public class Project {
    /**
     * 项目ID
     */
    @TableId(type = IdType.AUTO)
    private Long pid;
    /**
     * 项目名称
     */
    @TableField("name")
    private String name;
    /**
     * 项目主管
     */
    @TableField("charge")
    private String charge;
    /**
     * 项目描述
     */
    @TableField("note")
    private String note;
    /**
     * 项目状态
     * 逻辑删除标记，0表示正常，1表示删除
     */
    @TableLogic     // 标记为逻辑删除字段
    @TableField("status")
    private Integer status;
}
