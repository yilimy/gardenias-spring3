package org.example.mock.spring;

/**
 * 回调：获取bean的名字
 * @author caimeng
 * @date 2024/6/13 16:02
 */
public interface IBeanNameAware {
    void setBeanName(String beanName);
}
