package com.ssm.mybatis.plus.dao;

import com.ssm.mybatis.plus.mapper.IBaseMapper;
import com.ssm.mybatis.plus.vo.Project;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

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
    @SuppressWarnings("SqlWithoutWhere")    // 本就是来测试MP的全表更新拦截
    @Update(value = "UPDATE project SET charge='小李老师'")
    long doUpdateAll();

    /**
     * 全部删除
     * <p>
     *     测试全删防护
     * @return 删除条数
     */
    @SuppressWarnings("SqlWithoutWhere")    // 本就是来测试MP的全表删除拦截
    @Delete(value = "DELETE FROM project")
    long doDeleteAll();

    /**
     * 测试：没有索引的查询
     * @param charge 项目主管
     * @return 查询结果
     */
    @Select("SELECT pid, name, charge, note, status, version, tenant_id FROM project WHERE charge=#{charge}")
    List<Project> findAllByCharge(String charge);

    Project findById2(Long id);
}
