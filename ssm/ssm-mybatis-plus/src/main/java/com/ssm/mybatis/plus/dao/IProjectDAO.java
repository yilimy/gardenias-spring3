package com.ssm.mybatis.plus.dao;

import com.ssm.mybatis.plus.mapper.IBaseMapper;
import com.ssm.mybatis.plus.vo.Project;
import org.apache.ibatis.annotations.Mapper;

/**
 * @date 2024-12-23 实现类从 BaseMapper 改为自定义的 IBaseMapper，新增了自定义的 findAll 方法
 * @author caimeng
 * @date 2024/12/23 10:07
 */
@Mapper
public interface IProjectDAO extends IBaseMapper<Project> {
}
