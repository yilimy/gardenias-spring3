package com.example.boot3.aware.bind;

import com.example.boot3.aware.source.DataBaseConfig;
import org.springframework.beans.factory.Aware;

/**
 * 定义可实现的DataBaseConfig对象实例配置注入的接口，该接口要实现Aware父接口
 * Aware仅仅作为一个标记
 * 有aware，必然会有处理接口
 * @see com.example.boot3.aware.post.DataBaseBeanPostProcessor
 * @author caimeng
 * @date 2024/4/16 14:26
 */
public interface IDataBaseAware extends Aware {

    /*
     * 自动注入DataBaseConfig实例
     * 具体的注入处理操作是由BeanPostProcessor接口支持的
     */
    void setDataBaseConfig(DataBaseConfig dataBaseConfig);
}
