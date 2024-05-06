package com.example.boot3.aop.service.impl;

import com.example.boot3.aop.service.IDeptService;
import com.example.boot3.aop.vo.Dept;
import lombok.extern.slf4j.Slf4j;

/**
 * @author caimeng
 * @date 2024/5/6 17:32
 */
@Slf4j
public class DeptServiceImpl implements IDeptService {
    /**
     * 数据增加
     * 如果按照正常的数据库开发代码的话，此时应该是需要进行事务处理的，而后对事务处理又需要考虑到在业务层的实现。
     * 毕竟有可能会跨越到多个和数据层的处理，
     * 但是，如果要在业务层编写，必然会造成大量的代码侵入，对业务层的实现不利。
     * 最佳的做法是进行代理结构的配置。
     * @param dept 部门信息
     * @return 新增是否成功
     */
    @Override
    public boolean add(Dept dept) {
        // 手动开启事务
        log.info("【部门增加】: {}", dept);
        // 手动关闭事务
        return true;
    }
}
