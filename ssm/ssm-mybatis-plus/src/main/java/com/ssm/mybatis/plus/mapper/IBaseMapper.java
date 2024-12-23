package com.ssm.mybatis.plus.mapper;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import org.apache.ibatis.builder.MapperBuilderAssistant;

import java.util.List;

/**
 * 自定义的BaseMapper
 * <p>
 *     MyBatisPlus拼接SQL的操作，是通过实现方法(injectMappedStatement)来实现的。
 *     注入器
 *     {@link AbstractMethod#inject(MapperBuilderAssistant, Class, Class, TableInfo)}
 *     子类实现
 *     {@link AbstractMethod#injectMappedStatement(Class, Class, TableInfo)}
 * @author caimeng
 * @date 2024/12/23 16:57
 */
public interface IBaseMapper<T> extends BaseMapper<T> {
    /**
     * 查询所有数据
     * @return 数据列表
     */
    List<T> findAll();      // 扩充的数据方法
}
