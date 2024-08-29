package com.example.ssm.mvcb.action;

import com.example.ssm.mvcb.service.IMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.PathVariable;
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
@RequestMapping("/pages/message")
public class MessageAction {
    @Autowired
    private IMessageService messageService;// 业务实例

    /**
     * 访问 <a href="http://localhost/pages/message/echo?message=123456" /> 查看结果
     * @param message 请求参数
     * @return 视图对象
     */
    @RequestMapping("/echo")// 映射地址
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
    @RequestMapping("/echoPath")// 映射地址
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

    /**
     * 由该页面提供输入页面，提交后跳转到 {@link MessageAction#echo2(String)} 所指向的页面
     * 访问 <a href="http://localhost/pages/message/input" /> 查看结果
     * @return jsp页面路径
     */
    @GetMapping("/input")
    public String input(){
        // 由于此处不需要进行任何的值传递，所以直接返回字符串路径
        return "/pages/message/input.jsp";
    }

    /**
     * 接收来自 {@link MessageAction#input()} 页面提交的请求
     * @param message 表单提交的参数名
     * @return 数据内容对象
     */
    @RequestMapping("/echo2")// 映射地址
    public ModelAndView echo2(String message){
        log.info("消息回应处理,msg={}", message);
        ModelAndView mav = new ModelAndView("/pages/message/show2.jsp");
        mav.addObject("echoMessage", messageService.echo(message));
        return mav; // 路径的跳转
    }

    /**
     * 测试使用 PathVariable
     * 访问 <a href="http://localhost/pages/message/echo3/yootk/沐言科技：yootk.com/1" /> 查看结果
     * <p>
     *     在一些开发要求比较严格的环境下（很多国外的环境），会进行一些严格的路径组成上的定义，
     *     而这些定义的时候往往都要接收路径参数。
     * @return 数据内容对象
     */
    @GetMapping("/echo3/{title}/{info}/{level}")// 映射地址
    public ModelAndView echo2(
            @PathVariable(name = "title") String title,
            @PathVariable("info") String info,
            @PathVariable("level") String level){
        /*
         * 消息回应处理,title=yootk, info=沐言科技：yootk.com, level=1
         * 支持中文
         */
        log.info("消息回应处理,title={}, info={}, level={}", title, info, level);
        return null; // 路径的跳转
    }

    /**
     * 测试：矩阵参数传递
     * 访问 <a href="http://localhost/pages/message/matrix/1;title=yootk;content=www.yootk.com;level=2" /> 查看结果
     */
    @GetMapping("/matrix/{mid}")// 映射地址
    public ModelAndView matrix(
            @PathVariable(name = "mid") String mid, // 绑定路径参数
            @MatrixVariable("title") String title,
            @MatrixVariable("content") String content,
            @MatrixVariable("level") int level){
        log.info("消息回应处理, mid={}, title={}, content={}, level={}", mid, title, content, level);
        return null; // 路径的跳转
    }

    /**
     * 测试：矩阵参数传递，其二
     * 访问 <a href="http://localhost/pages/message/matrix_map/title=yootk;content=www.yootk.com;level=2" /> 查看结果
     * <p>
     *     与视频不一致，视频中使用的是
     *     <code>
     *         "/echo_map/{.*}"
     *     </code>
     *     但是我这边启动会报错:
     *          Char '.' not allowed at start of captured variable name
     *     改成：“{:.*}”,倒是能部分实现功能，但是会忽略掉第一个 ";" 前的内容 (title=yootk)
     *     e.g.
     *          content = www.yootk.com
     *          level = 2
     * <p>
     *     可能是矩阵参数要按规则 segment;key=value 来匹配
     * @see org.springframework.web.util.pattern.InternalPathPatternParser#parse(String)
     * @param params 矩阵参数
     */
    @GetMapping("/matrix_map/{}")
    public ModelAndView matrixMap(@MatrixVariable Map<String, String> params){
        params.forEach((k, v) -> System.out.printf("%s = %s\n", k, v));
        return null;
    }
}
