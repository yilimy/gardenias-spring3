package com.ssm.mybatis.plus.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

/**
 * 数据填充器
 * <p>
 *     用于自动填充数据，比如逻辑删除、更新时间等字段的填充
 * @author caimeng
 * @date 2024/12/23 14:30
 */
@Slf4j
@Component
public class ProjectMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        // 增加时填充
        log.info("【MetaObject】获取原始对象：{}", metaObject.getOriginalObject());
        log.info("【MetaObject】获取name属性内容：{}", metaObject.getValue("name"));
        log.info("【MetaObject】是否存在有getName方法：{}", metaObject.hasGetter("name"));
        log.info("【MetaObject】是否存在有setName方法：{}", metaObject.hasSetter("name"));
        // 在数据增加时，status字段的内容默认值为0
        this.strictInsertFill(metaObject, "status", Integer.class, 0);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        // 修改时填充
        this.strictInsertFill(metaObject, "status", Integer.class, 0);
    }
}
