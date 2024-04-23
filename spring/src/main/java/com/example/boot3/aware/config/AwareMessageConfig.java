package com.example.boot3.aware.config;

import com.example.boot3.aware.impl.MyDataBase;
import com.example.boot3.aware.post.DataBaseBeanPostProcessor;
import com.example.boot3.aware.source.DataBaseConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author caimeng
 * @date 2024/4/16 14:54
 */
@Configuration
public class AwareMessageConfig {

    @Bean
    public DataBaseConfig dataBaseConfig() {
        DataBaseConfig config = new DataBaseConfig();
        config.setName("mysql");
        config.setUrl("mysql://192.168.200.130");
        return config;
    }

    @Bean
    public MyDataBase myDataBase() {
        return new MyDataBase();
    }

    @Bean
    public DataBaseBeanPostProcessor dataBaseBeanPostProcessor() {
        return new DataBaseBeanPostProcessor();
    }
}
