package com.ssm.mybatis.plus.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
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
//    @TableId(type = IdType.AUTO)
    @TableId(type = IdType.ASSIGN_ID)
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
    @TableField(value = "status", fill = FieldFill.INSERT_UPDATE)       // 新增和修改都会触发填充器逻辑
    private Integer status;
    /**
     * 乐观锁
     */
    @Version
    @TableField("version")
    private Integer version;
    /**
     * 租户
     * <p>
     *     租户的列名有默认值
     *     {@link com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler#getTenantIdColumn()}
     */
    @TableField("tenant_id")
    private String tenantId;
}
