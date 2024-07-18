package com.example.boot3.spring.cache.dao;

import com.example.boot3.spring.cache.po.Emp2;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author caimeng
 * @date 2024/7/18 17:40
 */
public interface IEmp2Dao extends JpaRepository<Emp2, String> {

    /**
     * 根据名称查询
     * @param ename 名称
     * @return 查询结果
     */
    List<Emp2> findByEname(String ename);
}
