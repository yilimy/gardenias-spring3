package com.example.ssm.mvcb.action;

import com.example.ssm.mvcb.action.abs.AbstractAction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.Map;
import java.util.Set;

/**
 * @author caimeng
 * @date 2024/9/18 9:53
 */
@Slf4j
@Controller
@RequestMapping("/pages/user/*")
// 配置session属性的名称
@SessionAttributes({"user", "role"})
public class UserAction extends AbstractAction {

    /**
     * 模拟：session
     * <a href="http://localhost/pages/user/login" />
     * <p>
     *     在 SessionAttributes 中配置了属性；
     *     在 ModelMap 中定义了属性
     * @param modelMap modelMap
     * @return 页面地址
     */
    @RequestMapping("/login")
    public String login(ModelMap modelMap) {
        modelMap.addAttribute("user",
                Map.of("id", "yootk", "password", "helloMuYan"));
        modelMap.addAttribute("role",
                Set.of("company", "dept", "emp"));
        return "/pages/user/login_success.jsp";
    }
}
