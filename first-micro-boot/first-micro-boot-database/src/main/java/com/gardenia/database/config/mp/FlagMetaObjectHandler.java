package com.gardenia.database.config.mp;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.gardenia.database.config.MybatisPlusConfig;
import com.gardenia.database.vo.DeptPO;
import com.gardenia.database.vo.EmpPO;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;

/**
 * 元对象字段填充控制器 <br>
 * 需要在 mybatis-plus 中进行配置
 * {@link MybatisPlusConfig#iMybatisSqlSessionFactoryBean(
 *                  javax.sql.DataSource,
 *                  org.springframework.core.io.Resource,
 *                  String, String, String, String)}
 * @author caimeng
 * @date 2025/5/27 16:06
 */
@Slf4j
public class FlagMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        if (DeptPO.class.equals(metaObject.getOriginalObject().getClass())) {
            log.info("【FlagMetaObjectHandler】DeptPO insertFill: {}", metaObject.getOriginalObject());
            setFieldValByName("flag", "【insert】DeptPO", metaObject);
        } else if (EmpPO.class.equals(metaObject.getOriginalObject().getClass())) {
            log.info("【FlagMetaObjectHandler】EmpPO insertFill: {}", metaObject.getOriginalObject());
            setFieldValByName("flag", "【insert】EmpPO", metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        if (DeptPO.class.equals(metaObject.getOriginalObject().getClass())) {
            log.info("【FlagMetaObjectHandler】DeptPO updateFill: {}", metaObject.getOriginalObject());
            setFieldValByName("flag", "【update】DeptPO", metaObject);
        } else if (EmpPO.class.equals(metaObject.getOriginalObject().getClass())) {
            log.info("【FlagMetaObjectHandler】DeptPO updateFill: {}", metaObject.getOriginalObject());
            setFieldValByName("flag", "【update】EmpPO", metaObject);
        }
    }
}
