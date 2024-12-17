package com.ssm.mybatis.service;

import com.ssm.mybatis.StartMyBatisApplication;
import com.ssm.mybatis.vo.Message;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

/**
 * @author caimeng
 * @date 2024/12/17 11:56
 */
@ContextConfiguration(classes = StartMyBatisApplication.class)  // 配置启动类
@ExtendWith(SpringExtension.class)
public class IMessageServiceTest {
    @Autowired
    private IMessageService iMessageService;

    @Test
    public void addTest() {
        Message message = new Message();
        message.setTitle("原创编程图书");
        message.setSender("李兴华");
        message.setContent("《SSM开发实战》");
        System.out.println("【增加消息】message : " + message);
        boolean add = iMessageService.add(message);
        System.out.println("【增加消息】结果 : " + add);
    }

    @Test
    public void addWithAnnotationTest() {
        Message message = new Message();
        message.setTitle("原创创世图书");
        message.setSender("莉莉丝");
        message.setContent("《改天换地开发实战》");
        System.out.println("【增加消息】message : " + message);
        /*
         * Preparing: INSERT INTO message(title, sender, content) VALUES (?, ?, ?)
         * Preparing: SELECT LAST_INSERT_ID()
         */
        boolean add = iMessageService.addWithAnnotation(message);
        System.out.println("【增加消息】结果 : " + add);
    }

    @Test
    public void findDetailsTest() {
        /*
         * Preparing: SELECT mid, title, sender, content FROM message LIMIT ?, ?
         * Parameters: 0(Integer), 2(Integer)
         */
        List<Message> list = iMessageService.list(1, 2);
        list.forEach(System.out::println);
    }

    @Test
    public void findDetailsWithAnnotationTest() {
        // Preparing: SELECT mid, title, sender, content FROM message LIMIT ?, ?
        List<Message> list = iMessageService.listWithAnnotation(1, 2);
        list.forEach(System.out::println);
    }

}
