package com.example.boot3.datajpa.dao;

import com.example.boot3.datajpa.po.Company;
import org.springframework.data.repository.Repository;

import java.util.Collection;
import java.util.List;

/**
 * 基于方法映射的数据层的持久化处理服务
 * 方法映射参考官网：<a href="https://docs.spring.io/spring-data/jpa/docs/3.0.0-M4/reference/html/#jpa.query-methods" />
 * 相较于 {@link ICompanyInterfaceDao},该类使用方法映射替代了注解
 * @author caimeng
 * @date 2024/6/28 10:13
 */
public interface ICompanyMapper extends Repository<Company, Long> {

    /**
     * 查询大于指定注册资本的公司
     * @param capital 注册资本
     * @return 公司列表
     */
    List<Company> findByCapitalGreaterThan(double capital);

    /**
     * 查询包含ID的所有数据
     * @param ids 待查询ID
     * @return 数据集
     */
    List<Company> findByCidIn(Collection<Long> ids);

    /**
     * 根据名字做模糊查询，并倒序排列
     * @param nameLike 模糊查询词
     * @return 数据列表
     */
    List<Company> findByNameLikeOrderByCidDesc(String nameLike);

    /**
     * 根据名称和注册地查询，结果倒序排列
     * @param name 公司名称
     * @param place 注册地
     * @return 结果集
     */
    List<Company> findByNameAndPlaceOrderByCidDesc(String name, String place);
}
