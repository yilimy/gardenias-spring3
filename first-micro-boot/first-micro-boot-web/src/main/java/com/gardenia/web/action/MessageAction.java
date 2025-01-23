package com.gardenia.web.action;

import com.gardenia.common.action.abs.AbstractBaseAction;
import com.gardenia.web.service.IMessageService;
import com.gardenia.web.vo.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author caimeng
 * @date 2025/1/20 11:18
 */
@Slf4j
@RestController     // SpringMVC中的注解，基于Rest架构的处理
@RequestMapping("/message/*")   // 添加父路径
public class MessageAction extends AbstractBaseAction {     // 继承抽象类，完成字符串向日期型的转换
    @Autowired
    @Qualifier("iMessageServiceXmlImpl")
    private IMessageService iMessageService;
    @RequestMapping("/echo")    // 子路径
    public String echo(String msg) {    // 进行请求参数的接收和请求内容的回应
        log.info("接收msg的请求参数，内容为:{}", msg);
        return iMessageService.echo(msg);
    }
    @RequestMapping("/home")    // 映射目录
    public String home() {
        return "Hello World!";
    }   // 响应的数据信息

    /**
     * 请求中含有Date类型参数，传参为字符串
     * @param message 请求对象
     * @return 修正后的请求对象
     */
    @RequestMapping("/transStrToDate")    // 映射目录
    public Object transStrToDate(Message message) {
        message.setTitle("【ECHO】" + message.getTitle());
        message.setContent("【ECHO】" + message.getContent());
        /*
         * {
         *    "title": "【ECHO】消息标题",
         *    "pubDate": "2025-01-23T00:00:00.000+00:00",
         *    "content": "【ECHO】消息内容"
         *  }
         */
        return message;
    }
}
