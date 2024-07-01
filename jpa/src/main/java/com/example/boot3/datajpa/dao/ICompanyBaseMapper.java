package com.example.boot3.datajpa.dao;

import com.example.boot3.datajpa.po.Company;
import org.springframework.data.repository.CrudRepository;

/**
 * 自动实现CRUD的数据层处理服务接口
 * 相较于 {@link ICompanyMapper}, 该类继承自 Repository 的子接口 CrudRepository
 * @author caimeng
 * @date 2024/6/28 13:59
 */
public interface ICompanyBaseMapper extends CrudRepository<Company, Long> {
}
