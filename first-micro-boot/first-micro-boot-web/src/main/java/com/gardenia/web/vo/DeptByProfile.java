package com.gardenia.web.vo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author caimeng
 * @date 2025/2/6 9:54
 */
@Data
// 指定配置文件
@PropertySource(value = "classpath:muyan.properties", encoding = "UTF-8")
// 因为指定配置文件后，属性仍会被application.yml覆盖，因此换一个标签
@ConfigurationProperties(prefix = "object1")
// 添加到spring扫描才能使 ConfigurationProperties 生效
@Component
public class DeptByProfile {
    private Long deptNo;
    private String dName;
    // 关联一个对象
    private Company company;
    // 以横杠方式注入的属性
    private List<Emp> emps;     // 关联结构
    // 以数组方式注入的属性
    private List<Emp> empList;
}
