package org.example.mock.service.config;

import org.example.mock.spring.IComponentScan;

import java.util.Map;
import java.util.Properties;

/**
 * 传递给容器的配置类
 * @author caimeng
 * @date 2024/6/12 18:59
 */
// 提供扫描路径
@IComponentScan({"org.example.mock.service.beans", "org.example.mock.service.ability"})
public class AppConfig {

    /**
     * 是否要打印
     * @param clazz 类
     * @return 是否要打印
     */
    public static boolean printEnable(Class<?> clazz) {
        final String clazzName = clazz.getName();
        Properties properties = System.getProperties();
        return properties.entrySet().stream()
                .filter(entry -> {
                    String key = String.valueOf(entry.getKey());
                    if (key.startsWith("print.")) {
                        String toMatchStr = key.substring(6);
                        return clazzName.startsWith(toMatchStr);
                    }
                    return false;
                })
                .map(Map.Entry::getValue)
                .findAny()
                .map(Object::toString)
                .map(Boolean::valueOf)
                .orElse(true);
    }
}
