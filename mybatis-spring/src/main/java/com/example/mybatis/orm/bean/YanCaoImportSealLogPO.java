package com.example.mybatis.orm.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.validation.constraints.Max;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 烟草导入印章记录的实体类
 * @author caimeng
 * @date 2024/6/25 11:37
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("ps_yc_import_seal_log")
@Data
public class YanCaoImportSealLogPO {
    @TableId
    private Long id;
    @Max(50)
    @TableField
    private String sealCode;
    @Max(100)
    @TableField
    private String sealName;
    @TableField
    private Long sealId;
    @TableField
    private Date updateTime;
}
