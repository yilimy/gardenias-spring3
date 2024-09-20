package com.example.ssm.mvcb.action;

import com.example.ssm.mvcb.action.abs.AbstractAction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author caimeng
 * @date 2024/9/20 15:32
 */
@Slf4j
@Controller
@RequestMapping("/pages/error")
public class ErrorAction extends AbstractAction {

    /**
     * 模拟一次异常
     * 访问 <a href="http://localhost/pages/error/echo?message=error" /> 查看结果
     * 需要配置文件的支持 security.enable=true
     * {@link com.example.ssm.mvcb.config.ResourceViewConfig}
     * @param message 输入指令
     * @return 视图对象
     */
    @RequestMapping("/echo")// 映射地址
    public ModelAndView echo(String message){
        if("error".equals(message)) {
            throw new RuntimeException("引爆群星");
        }
        ModelAndView mav = new ModelAndView("/message/show");
        mav.addObject("echoMessage", "【ECHO】" + message);
        return mav;
    }
}
