package com.example.ssm.mvc.action;

import com.example.ssm.mvc.service.IMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 控制层注解
 * @author caimeng
 * @date 2024/8/27 14:18
 */
@Slf4j
@Controller
public class MessageAction {
    @Autowired
    private IMessageService messageService;// 业务实例

    /**
     * 访问 <a href="http://localhost/pages/message/echo?message=123456" /> 查看结果
     * @param message 请求参数
     * @return 视图对象
     */
    @RequestMapping("/pages/message/echo")// 映射地址
    public ModelAndView echo(String message){
        log.info("消息回应处理,msg={}", message);
        // 配置视图
        ModelAndView mav = new ModelAndView("/pages/message/show.jsp");
        // 在传统的MVC中，控制层需要传递属性到视图层，所以利用ModelAndView进包装处理
        mav.addObject("echoMessage", messageService.echo(message)); // 业务调用
        return mav; // 路径的跳转
    }
}
