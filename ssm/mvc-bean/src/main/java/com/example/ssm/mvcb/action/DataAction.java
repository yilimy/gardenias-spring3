package com.example.ssm.mvcb.action;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author caimeng
 * @date 2024/9/3 10:10
 */
@Slf4j
@Controller
@RequestMapping("/pages/data/*")
public class DataAction {

    /**
     * 客户端跳转
     * 跳转后的地址 {@link DataAction#listParam(String, String)}
     * 访问地址： <a href="http://localhost/pages/data/set_param" />
     * 跳转地址：{@link DataAction#listParam(String, String)}
     *      但是该方式跳转之后的方法能接收到数据，页面没有接收到数据。
     * 如何使页面也接收到数据？{@link DataAction#setFlash(RedirectAttributes)}
     * @param attributes 数据传递对象
     * @return 跳转路径
     */
    @RequestMapping("/set_param")
    public String setParam(RedirectAttributes attributes){
        /*
         * 属性设置
         * 显示参数
         */
        attributes.addAttribute("name", "沐言科技");
        attributes.addAttribute("url", "yootk.com");
        return "redirect:/pages/data/list_param";    // 跳转到其他控制层的方法
    }

    /**
     * 跳转后的页面，并接收跳转后的数据
     * @param name 接收参数其一
     * @param url 接收参数其二
     * @return 页面地址
     */
    @RequestMapping("/list_param")
    public String listParam(String name, String url){
        log.info("【Redirect参数接收】name={}, url={}", name, url);
        return "/pages/data/show.jsp";
    }

    /**
     * 客户端跳转
     * 访问地址： <a href="http://localhost/pages/data/set_flash" />
     * <p>
     *     相较于 {@link DataAction#setParam(RedirectAttributes)} 使用的是 addFlashAttribute 方法
     *     跳转之后使用的是 ModelMap 接收，这样页面也能接收到跳转传递的参数
     * 跳转地址：{@link DataAction#listFlash(ModelMap)}
     * @param attributes 数据传递对象
     * @return 跳转路径
     */
    @RequestMapping("/set_flash")
    public String setFlash(RedirectAttributes attributes){
        /*
         * 隐式参数，能把数据传递给跳转后的页面
         */
        attributes.addFlashAttribute("name", "沐言科技");
        attributes.addFlashAttribute("url", "yootk.com");
        return "redirect:/pages/data/list_flash";    // 跳转到其他控制层的方法
    }

    @RequestMapping("/list_flash")
    public String listFlash(ModelMap modelMap){
        log.info("【Redirect参数接收】modelMap={}", modelMap);
        return "/pages/data/show.jsp";
    }
}
