package com.ssm.mybatis.plus.dao;

import com.ssm.mybatis.plus.mapper.IBaseMapper;
import com.ssm.mybatis.plus.vo.Project;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

/**
 * @date 2024-12-23 实现类从 BaseMapper 改为自定义的 IBaseMapper，新增了自定义的 findAll 方法
 * @author caimeng
 * @date 2024/12/23 10:07
 */
@Mapper
public interface IProjectDAO extends IBaseMapper<Project> {

    /**
     * 全部更新
     * <p>
     *     测试全更防护
     * @return 更新条数
     */
    @Update(value = "UPDATE project SET charge='小李老师'")
    long doUpdateAll();

    /**
     * 全部删除
     * <p>
     *     测试全删防护
     * @return 删除条数
     */
    @Delete(value = "DELETE FROM project")
    long doDeleteAll();
}
