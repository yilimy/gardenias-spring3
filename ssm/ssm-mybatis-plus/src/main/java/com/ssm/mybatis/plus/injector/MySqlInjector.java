package com.ssm.mybatis.plus.injector;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.ssm.mybatis.plus.mapper.impl.FindAll;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 自定义SQL注入器
 * @author caimeng
 * @date 2024/12/23 17:19
 */
@Component
public class MySqlInjector extends DefaultSqlInjector {

    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass, TableInfo tableInfo) {
        List<AbstractMethod> methodList = super.getMethodList(mapperClass, tableInfo);
        // 追加新配置的方法映射
        methodList.add(new FindAll());
        return methodList;
    }
}
