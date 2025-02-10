package com.gardenia.web;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * 测试：邮件发送
 * <p>
 *     不要循环发送，有封杀的风险
 * @author caimeng
 * @date 2025/2/10 15:17
 */
@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@SpringBootTest(classes = MicroBootWebApplication.class)
public class SpringMailTest {
    @Autowired
    private JavaMailSender javaMailSender;      // 发送邮件的工具类
    @Test
    public void sendTest() {
        // 建立一个简单的邮件结构
        SimpleMailMessage message = new SimpleMailMessage();
        // 邮件的发送方
        message.setFrom("1667565548@qq.com");
        // 邮件的接收方
        message.setTo("yilimy102@163.com");
        // 主题 | 标题
        message.setSubject("测试邮件发送");
        // 内容
        message.setText("沐言科技（www.yootk.com）的测试邮件");
        // 执行发送
        javaMailSender.send(message);
    }
}
