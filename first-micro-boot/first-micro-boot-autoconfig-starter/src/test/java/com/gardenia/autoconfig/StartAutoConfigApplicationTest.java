package com.gardenia.autoconfig;

import com.gardenia.autoconfig.config.YootkConfiguration;
import com.gardenia.autoconfig.vo.DeptAutoConfig;
import com.gardenia.autoconfig.vo.DeptWithBeanDefinition;
import com.gardenia.autoconfig.vo.DeptWithImport;
import com.gardenia.autoconfig.vo.DeptWithSelector;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @author caimeng
 * @date 2025/5/15 16:16
 */
@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@SpringBootTest(classes = StartAutoConfigApplication.class)
public class StartAutoConfigApplicationTest {
    @Autowired
    private DeptAutoConfig deptAutoConfig;
    @Autowired
    // 通过“前缀 + 横杠 + 类全名”实现唯一的标记，可选
    // 如果遇见重复的 Bean 类型注入，可以通过该方法指定 Bean
    @Qualifier("gardenia.dept-com.gardenia.autoconfig.vo.DeptAutoConfig")
    private DeptAutoConfig deptAutoConfig1;
    @Autowired
//    @Qualifier("gardenia.dept2-com.gardenia.autoconfig.vo.DeptWithImport")
    private DeptWithImport deptWithImport;
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private DeptWithSelector deptWithSelector;
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private DeptWithBeanDefinition deptWithBeanDefinition;

    @Test
    public void deptTest() {
        System.out.println(deptAutoConfig);
    }

    @Test
    public void dept1Test() {
        System.out.println(deptAutoConfig1);
    }

    @Test
    public void beanInfoTest() {
        AnnotationConfigApplicationContext  context =
                new AnnotationConfigApplicationContext(YootkConfiguration.class);
        // 获取bean的名称集合
        String[] beanDefinitionNames = context.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            /*
             * ...
             * 【yootkConfiguration】 YootkConfiguration$$SpringCGLIB$$0
             * ...
             * 【gardenia.dept-com.gardenia.autoconfig.vo.Dept】 Dept
             */
            System.out.println("【" + beanDefinitionName + "】 "
                    + context.getBean(beanDefinitionName).getClass().getSimpleName());
        }
    }

    @Test
    public void deptWithImportTest() {
        // DeptWithImport(deptno=11, dname=后勤部（www.yootk.com）, loc=北京)
        System.out.println(deptWithImport);
    }

    @Test
    public void deptWithSelectorTest() {
        // DeptWithSelector(deptno=12, dname=组织部（www.yootk.com）, loc=广州)
        System.out.println(deptWithSelector);
    }

    @Test
    public void deptWithBeanDefinitionTest() {
        // DeptWithBeanDefinition(deptno=13, dname=宣传部（www.yootk.com）, loc=深圳)
        System.out.println(deptWithBeanDefinition);
    }
}
