package com.example.ssm.mvc.action;

import com.example.ssm.mvc.service.IMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

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
        // 业务层很多时候也是会返回Map集合，那么常规的做法是将Map集合迭代保存在属性范围之中
        Map<String, Object> result = new HashMap<>();
        result.put("yootk", "沐言科技: www.yootk.com");
        result.put("edu", "李兴华编程训练营");
        mav.addAllObjects(result);
        return mav; // 路径的跳转
    }

    /**
     * 通过返回页面路径的方式，响应消息
     * <p>
     *     相比较用 ModelAndView 显式的返回对象，使用 Model 返回对象有点类似于C的方法出参
     * @param message 请求参数
     * @param model 内容存储对象
     * @return 返回的页面
     */
    @RequestMapping("/pages/message/echoPath")// 映射地址
    public String echoPath(String message, Model model){
        log.info("消息回应处理2,msg={}", message);
        // 对应 mav.addObject
        model.addAttribute("echoMessage", messageService.echo(message));
        Map<String, Object> result = new HashMap<>();
        result.put("yootk", "沐言科技: www.yootk.com");
        result.put("edu", "李兴华编程训练营");
        // 对应 mav.addAllObjects
        model.addAllAttributes(result);
        // 页面没有变，还是 show.jsp
        return "/pages/message/show.jsp";
    }
}
