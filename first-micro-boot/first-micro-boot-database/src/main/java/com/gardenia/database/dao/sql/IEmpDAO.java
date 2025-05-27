package com.gardenia.database.dao.sql;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gardenia.database.vo.EmpPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author caimeng
 * @date 2025/5/27 16:23
 */
@Mapper
public interface IEmpDAO extends BaseMapper<EmpPO> {
}
