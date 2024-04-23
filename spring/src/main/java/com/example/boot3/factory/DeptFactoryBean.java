package com.example.boot3.factory;

import com.example.boot3.bean.DeptBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author caimeng
 * @date 2024/4/11 10:56
 */
@Component
public class DeptFactoryBean implements FactoryBean<DeptBean> {
    @Override
    public DeptBean getObject() throws Exception {
        return DeptBean.builder().deptNo(UUID.randomUUID().toString()).deptName("deptName").loc("loc").build();
    }

    @Override
    public Class<?> getObjectType() {
        return DeptBean.class;
    }
}
