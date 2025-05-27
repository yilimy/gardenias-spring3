package com.gardenia.database.service;

import java.util.Map;

/**
 * @author caimeng
 * @date 2025/5/27 16:32
 */
public interface CompanyService {
    /**
     * @return 部门和雇员的集合
     */
    Map<String, Object> list();
}
