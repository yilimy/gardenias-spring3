package com.example.boot3.aware.impl;

import com.example.boot3.aware.bind.IDataBaseAware;
import com.example.boot3.aware.source.DataBaseConfig;

/**
 * @author caimeng
 * @date 2024/4/16 14:51
 */
public class MyDataBase implements IDataBaseAware {
    private DataBaseConfig config;
    @Override
    public void setDataBaseConfig(DataBaseConfig dataBaseConfig) {
        this.config = dataBaseConfig;
    }

    public DataBaseConfig getConfig() {
        return config;
    }
}
