package com.gardenia.web.vo;

import com.gardenia.web.MicroBootWebApplication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * 测试: ConfigurationProperties
 * @author caimeng
 * @date 2025/1/24 18:15
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MicroBootWebApplication.class)
public class MuYanTest {
    @Autowired
    private MuYan muYan;
    @Test
    public void muYanTest() {
        /*
         * MuYan = MuYan(
         * mysql=mysql://localhost:3306/gardenia,
         * redis=redis://localhost:6379/gardenia,
         * messages=[
         *      沐言科技：www.yootk.com,
         *      李兴华的高薪就业编程训练营：www.edu.yootk.com
         * ],
         * books={
         *      javaBase=《java面向对象就业编程实战》,
         *      javaApplication=《java应用技术就业编程实战》,
         *      javaWeb=《javaWeb就业编程实战》,
         *      springBoot=《SpringBoot就业编程实战》,
         *      springCloud=《SpringCloud就业编程实战》
         * })
         */
        System.out.println("MuYan = " + muYan);
    }
}
