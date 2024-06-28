package com.example.boot3.datajpa.dao;

import com.example.boot3.datajpa.po.Company;
import org.springframework.data.domain.Example;
import org.springframework.data.repository.RepositoryDefinition;

import java.util.List;

/**
 * 数据层的持久化处理服务
 * <p>
 *     定义当前数据层对应的持久化类的类型，以及对应的主键数据类型
 * <p>
 *     方法名为什么这么定义？
 *     查看 {@link org.springframework.data.repository.Repository} 的子接口
 *     {@link org.springframework.data.repository.CrudRepository#save(Object)}
 *     {@link org.springframework.data.jpa.repository.JpaRepository#findAll(Example)}
 * <p>
 *     迭代路线:
 *     {@link ICompanyDao}
 *     {@link ICompanyInterfaceDao}
 *     {@link ICompanyMapper}
 *     {@link ICompanyBaseMapper}
 * @author caimeng
 * @date 2024/6/27 16:53
 */
@RepositoryDefinition(domainClass = Company.class, idClass = Long.class)
public interface ICompanyDao {
    /**
     * 实现数据增加
     * @param company 数据实体
     * @return 增加后的对象数据
     */
    Company save(Company company);

    /**
     * 查询所有
     * @return 数据实例列表
     */
    List<Company> findAll();
}
