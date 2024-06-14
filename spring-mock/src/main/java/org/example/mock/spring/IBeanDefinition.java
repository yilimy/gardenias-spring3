package org.example.mock.spring;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * bean的定义
 * @author caimeng
 * @date 2024/6/13 11:38
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IBeanDefinition {
    /**
     * bean的类型
     */
    private Class<?> type;
    /**
     * 作用域
     * 单例还是多例
     */
    private String scope;
}
