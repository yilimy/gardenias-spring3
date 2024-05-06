package com.example.boot3.aop.service;

import com.example.boot3.aop.vo.Dept;

/**
 * @author caimeng
 * @date 2024/5/6 17:28
 */
public interface IDeptService {
    /**
     * 数据增加
     * @param dept 部门信息
     * @return 新增是否成功
     */
    boolean add(Dept dept);
}
