package com.ssm.mybatis.plus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ssm.mybatis.plus.dao.IProjectDAO;
import com.ssm.mybatis.plus.service.IProjectService;
import com.ssm.mybatis.plus.vo.Project;
import org.springframework.stereotype.Service;

/**
 * @author caimeng
 * @date 2024/12/26 10:10
 */
@Service
public class IProjectServiceImpl
    // 告知MP数据操作对象和数据实体
        extends ServiceImpl<IProjectDAO, Project>
    // IService 定义标准化CRUD接口
        implements IProjectService {
}
