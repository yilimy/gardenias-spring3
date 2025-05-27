package com.gardenia.database.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author caimeng
 * @date 2025/5/27 15:56
 */
@Data
@TableName("emp_multiple")
public class EmpPO {
    @TableId(type = IdType.ASSIGN_ID)   // 手工配置
    private String eid;
    private String ename;
    private Double salary;
    private Long did;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String flag;
}
