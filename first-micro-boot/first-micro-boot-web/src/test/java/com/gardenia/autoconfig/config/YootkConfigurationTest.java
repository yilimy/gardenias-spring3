package com.gardenia.autoconfig.config;

import com.gardenia.autoconfig.vo.DeptAutoConfig;
import com.gardenia.autoconfig.vo.DeptWithBeanDefinition;
import com.gardenia.autoconfig.vo.DeptWithImport;
import com.gardenia.autoconfig.vo.DeptWithSelector;
import com.gardenia.web.MicroBootWebApplication;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

/**
 * 测试，自动装配
 * @author caimeng
 * @date 2025/5/16 13:54
 */
@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@SpringBootTest(
        classes = MicroBootWebApplication.class,
        properties = {
                "gardenia.dept2.deptno=2",
                "gardenia.dept2.dname=name2",
                "gardenia.dept2.loc=loc2",
                "gardenia.dept3.deptno=3",
                "gardenia.dept3.dname=name3",
                "gardenia.dept3.loc=loc3",
                "gardenia.dept4.deptno=4",
        }
)
@ComponentScan({"com.gardenia.autoconfig.vo"})
public class YootkConfigurationTest {
    @Autowired
    private DeptAutoConfig deptAutoConfig;
    @Autowired
    private DeptWithImport deptWithImport;
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private DeptWithSelector deptWithSelector;
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private DeptWithBeanDefinition deptWithBeanDefinition;
    @Autowired
    @Qualifier(value = "books")     // 如果指定bean的名字，会优先匹配打印日志的bean(testLog)的值
    private List<String> books;

    @PostConstruct
    public void init() {
        System.out.println("✅ 自动配置类 YootkConfiguration 已加载");
    }

    @Test
    public void deptTest() {
        // DeptAutoConfig(deptno=1, dname=教学研发部, loc=Beijing)
        System.out.println(deptAutoConfig);
        System.out.println("-".repeat(30));
        System.out.println(books);
    }

    @Test
    public void dept2Test() {
        // DeptWithSelector(deptno=2, dname=name2, loc=loc2)
        System.out.println(deptWithImport);
    }

    @Test
    public void dept3Test() {
        // DeptWithSelector(deptno=3, dname=name3, loc=loc3)
        System.out.println(deptWithSelector);
    }

    @Test
    public void dept4Test() {
        // DeptWithBeanDefinition(deptno=4, dname=null, loc=Beijing)
        System.out.println(deptWithBeanDefinition);
    }
}
