package com.example.boot3.spring.cache.service;

import com.example.boot3.spring.cache.po.Emp2;

/**
 * @author caimeng
 * @date 2024/7/18 17:43
 */
public interface IEmp2Service {
    /**
     * 编辑雇员信息
     * @param emp2 雇员信息
     * @return 编辑后的雇员信息
     */
    Emp2 edit(Emp2 emp2);

    /**
     * 删除雇员信息
     * @param eid 雇员ID
     * @return 删除结果
     */
    boolean delete(String eid);

    /**
     * 查询雇员信息
     * @param eid 雇员ID
     * @return 雇员信息
     */
    Emp2 get(String eid);

    /**
     * 根据名称查询雇员信息
     * @param ename 雇员名称
     * @return 雇员信息
     */
    Emp2 getByEname(String ename);
}
