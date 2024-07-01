package com.example.boot3.datajpa.dao;

import com.example.boot3.datajpa.po.Company;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JpaRepository 把该有的接口都定义了
 * @author caimeng
 * @date 2024/7/1 13:51
 */
public interface ICompanyJpaMapper extends JpaRepository <Company, Long> {
}
