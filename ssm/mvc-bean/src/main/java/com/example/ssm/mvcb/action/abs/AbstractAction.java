package com.example.ssm.mvcb.action.abs;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.beans.PropertyEditorSupport;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author caimeng
 * @date 2024/9/2 17:24
 */
public abstract class AbstractAction {
    private static final DateTimeFormatter LOCAL_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * 数据绑定方法
     * <p>
     *     如果子类对日期的格式有自己的想法，可以重写该数据绑定的方法.
     *     子类重写的方法优先生效。
     * @param binder web数据绑定对象
     */
    @InitBinder // 添加初始化的数据绑定注解
    public void initBinder(WebDataBinder binder){
        // 注册一个关于日期格式的转换器
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                LocalDate localDate = LocalDate.parse(text, LOCAL_DATE_FORMATTER);
                Instant instant = localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
                super.setValue(Date.from(instant));
            }
        });
    }
}
