/**
 * 自定义SQL注入器
 * <p>
 *     1. 自定义BaseMapper，对BaseMapper进行增强
 *          {@link com.ssm.mybatis.plus.mapper.IBaseMapper}
 *     2. 添加增强Mapper的实现
 *          {@link com.ssm.mybatis.plus.mapper.impl.FindAll}
 *     3. 将增强方法添加到映射列表（SQL注入器）
 *          {@link com.ssm.mybatis.plus.injector.MySqlInjector}
 *     4. 全局配置更新
 *          {@link com.ssm.mybatis.plus.config.MyBatisPlusConfig#globalConfig(ProjectMetaObjectHandler, SnowFlakeIdGenerator, MySqlInjector)}
 *          {@link com.ssm.mybatis.plus.config.MyBatisPlusConfig#sqlSessionFactoryBean(DataSource, GlobalConfig)}
 *     5. 业务类实现自定的BaseMapper，实现业务调用
 * @author caimeng
 * @date 2024/12/23 17:18
 */
package com.ssm.mybatis.plus.injector;

import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.ssm.mybatis.plus.generator.SnowFlakeIdGenerator;
import com.ssm.mybatis.plus.handler.ProjectMetaObjectHandler;

import javax.sql.DataSource;