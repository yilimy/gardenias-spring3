package com.example.boot3.bean;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 原型bean
 * @author caimeng
 * @date 2024/4/10 17:18
 */
@NoArgsConstructor
@Data
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UserBean {

    private String name;

    @Override
    public String toString() {
        return "[" + super.hashCode() + "] UserBean 对象实例";
    }

}
