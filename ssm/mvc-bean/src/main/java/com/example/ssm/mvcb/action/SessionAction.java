package com.example.ssm.mvcb.action;

import com.example.ssm.mvcb.action.abs.AbstractAction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * 测试session的控制层
 * <p>
 *     设置session
 *          {@link UserAction#login(ModelMap)}
 * @author caimeng
 * @date 2024/9/18 10:29
 */
@Slf4j
@Controller
@RequestMapping("/pages/session/*")
@SessionAttributes({"user", "role"})
public class SessionAction extends AbstractAction {

    /**
     * 获取session
     * <p>
     *  设置session
     *      <a href="http://localhost/pages/user/login" />
     *  获取session
     *      <a href="http://localhost/pages/session/get" />
     * @param modelMap modelMap
     * @return ModelAndView
     */
    @RequestMapping("/get")
    public ModelAndView get(ModelMap modelMap) {
        log.info("【用户信息】{}", modelMap.get("user"));
        log.info("【角色信息】{}", modelMap.get("role"));
        return null;
    }

    /**
     * session数据清除
     * <p>
     *  设置session
     *      <a href="http://localhost/pages/user/login" />
     *  获取session
     *      <a href="http://localhost/pages/session/get" />
     *  清除session
     *      <a href="http://localhost/pages/session/clean" />
     *  获取session
     *      <a href="http://localhost/pages/session/get" />
     * @return ModelAndView
     */
    @RequestMapping("/clean")
    public ModelAndView clean(Model model, SessionStatus status) {
        log.info("【用户信息】{}", model.getAttribute("user"));
        log.info("【角色信息】{}", model.getAttribute("role"));
        // 清除当前的session
        status.setComplete();
        log.info("session数据已被清除");
        return null;
    }
}
