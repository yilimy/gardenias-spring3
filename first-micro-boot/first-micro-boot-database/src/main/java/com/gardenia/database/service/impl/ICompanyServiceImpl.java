package com.gardenia.database.service.impl;

import com.gardenia.database.dao.qqq.IDeptDAO;
import com.gardenia.database.dao.sql.IEmpDAO;
import com.gardenia.database.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author caimeng
 * @date 2025/5/27 16:33
 */
@Service
public class ICompanyServiceImpl implements CompanyService {
    @Autowired
    private IDeptDAO deptDAO;
    @Autowired
    private IEmpDAO empDAO;
    @Override
    public Map<String, Object> list() {
        return Map.of(
                "dept", deptDAO.selectList(null),
                "emp", empDAO.selectList(null)
        );
    }
}
