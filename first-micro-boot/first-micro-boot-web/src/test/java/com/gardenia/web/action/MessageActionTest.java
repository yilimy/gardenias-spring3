package com.gardenia.web.action;

import com.gardenia.web.MicroBootWebApplication;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @author caimeng
 * @date 2025/1/21 17:23
 */
@ExtendWith(SpringExtension.class)  // 使用Junit5的测试工具
@WebAppConfiguration        // 启用web运行环境
@SpringBootTest(
        // 配置程序的启动类
        classes = MicroBootWebApplication.class
)
public class MessageActionTest {
    @Autowired      // 注入 MessageAction 的对象实例
    private MessageAction messageAction;
    @BeforeAll
    public static void init() {
        System.out.println("【@BeforeAll】MessageActionTest类开始执行测试操作");
    }

    @AfterAll
    public static void after() {
        System.out.println("【@AfterAll】MessageActionTest类测试完成");
    }

    @Test
    public void echoTest() {    // 进行响应测试
        String content = messageAction.echo("【ECHO】沐言科技 : www.yootk.com");
        String value = "【ECHO】沐言科技 : www.yootk.com";
        System.out.println("【@Test】测试echo()方法的返回值，当前方法的返回值为: " + content);
        Assertions.assertEquals(content, value);
    }
}
