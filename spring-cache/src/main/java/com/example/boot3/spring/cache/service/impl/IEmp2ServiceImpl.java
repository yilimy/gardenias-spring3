package com.example.boot3.spring.cache.service.impl;

import com.example.boot3.spring.cache.dao.IEmp2Dao;
import com.example.boot3.spring.cache.po.Emp2;
import com.example.boot3.spring.cache.service.IEmp2Service;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @author caimeng
 * @date 2024/7/18 17:50
 */
@Service
public class IEmp2ServiceImpl implements IEmp2Service {
    @Resource
    private IEmp2Dao iEmp2Dao;
    @Override
    public Emp2 edit(Emp2 emp2) {
        return iEmp2Dao.save(emp2);
    }

    @Override
    public boolean delete(String eid) {
        if (iEmp2Dao.existsById(eid)) {
            iEmp2Dao.deleteById(eid);
            return true;
        }
        return false;
    }

    @Override
    public Emp2 get(String eid) {
        return iEmp2Dao.findById(eid).orElse(null);
    }

    @Override
    public Emp2 getByEname(String ename) {
        return iEmp2Dao.findByEname(ename).get(0);
    }
}
