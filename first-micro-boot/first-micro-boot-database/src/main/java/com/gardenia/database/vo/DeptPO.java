package com.gardenia.database.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author caimeng
 * @date 2025/5/27 15:57
 */
@Data
@TableName("dept_multiple")
public class DeptPO {
    @TableId(type = IdType.AUTO)
    private Long did;
    private String dname;
    private String loc;
    /**
     * 填充器注解 {@link com.gardenia.database.config.mp.FlagMetaObjectHandler}
     */
    @TableField(fill = FieldFill.INSERT_UPDATE) // 有则更新，插入和更新时填充字段
    private String flag;
}
