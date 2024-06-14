package org.example.mock.service.beans;

import org.example.mock.spring.IBeanNameAware;
import org.example.mock.spring.IComponent;
import org.example.mock.spring.IInitializingBean;

/**
 * @author caimeng
 * @date 2024/6/12 18:46
 */
@IComponent("userService")
public class UserService implements IBeanNameAware, IInitializingBean {
    private String beanName;

    @Override
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public void testAware() {
        System.out.println("Aware : beanName = " + beanName);
    }

    @Override
    public void afterPropertiesSet() {
        System.out.println("UserService 执行了 afterPropertiesSet() 方法");
    }
}
