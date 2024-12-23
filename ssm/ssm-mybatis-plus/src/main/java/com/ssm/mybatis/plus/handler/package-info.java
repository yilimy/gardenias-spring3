/**
 * 数据填充器
 * <p>
 *     1. 自定义数据填充器
 *          {@link com.ssm.mybatis.plus.handler.ProjectMetaObjectHandler}
 *     2. 添加全局配置
 *          {@link com.ssm.mybatis.plus.config.MyBatisPlusConfig#globalConfig(ProjectMetaObjectHandler, SnowFlakeIdGenerator)}
 *          {@link com.ssm.mybatis.plus.config.MyBatisPlusConfig#sqlSessionFactoryBean(DataSource, GlobalConfig)}
 *     3. 实体类标记
 *          FieldFill.INSERT_UPDATE 新增和修改都会触发填充器逻辑
 *          {@link com.ssm.mybatis.plus.vo.Project#status}
 * @author caimeng
 * @date 2024/12/23 15:09
 */
package com.ssm.mybatis.plus.handler;

import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.ssm.mybatis.plus.generator.SnowFlakeIdGenerator;

import javax.sql.DataSource;