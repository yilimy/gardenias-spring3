package com.gardenia.thymeleaf.action;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author caimeng
 * @date 2025/2/24 14:55
 */
@Controller // 此时不是RestController ，所以返回的是视图
@RequestMapping("/thymeleaf/*")
public class ThymeleafAction {

    @RequestMapping("view")
    public ModelAndView view(String message) {
        /*
         * 不要加前缀templates，因为thymeleaf已经配置了视图解析器
         * 由 spring.thymeleaf.prefix 控制，默认值 : org.springframework.boot.autoconfigure.thymeleaf.ThymeleafProperties.DEFAULT_PREFIX
         */
        ModelAndView mv = new ModelAndView("message/message_show");
        mv.addObject("title", "沐言科技");
        mv.addObject("content", "www.yootk.com");
        mv.addObject("message", message);
        return mv;
    }

    @RequestMapping("view2")
    public String view2(String message, Model model) {
        model.addAttribute("title", "沐言科技");
        model.addAttribute("content", "www.yootk.com");
        model.addAttribute("message", message);
        return "message/message_show";
    }
}
