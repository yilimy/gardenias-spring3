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
 * @author caimeng
 * @date 2024/6/21 16:31
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("junit_user")
public class JunitUserFeaturePO implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 用户表主键
     */
    private Long userId;
    /**
     * 用户特征值
     */
    private String featureValue;
}
