package com.gardenia.autoconfig;

import com.gardenia.autoconfig.config.YootkConfiguration;
import com.gardenia.autoconfig.vo.Dept;
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
    private Dept dept;
    @Autowired
    // 通过“前缀 + 横杠 + 类全名”实现唯一的标记，可选
    // 如果遇见重复的 Bean 类型注入，可以通过该方法指定 Bean
    @Qualifier("gardenia.dept-com.gardenia.autoconfig.vo.Dept")
    private Dept dept1;

    @Test
    public void deptTest() {
        System.out.println(dept);
    }

    @Test
    public void dept1Test() {
        System.out.println(dept1);
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
}
