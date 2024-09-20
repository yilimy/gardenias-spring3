package com.example.ssm.mvcb.action;

import com.example.ssm.mvcb.action.abs.AbstractAction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author caimeng
 * @date 2024/9/20 16:16
 */
@Slf4j
@Controller
public class NotFoundAction extends AbstractAction {
    /**
     * 没有找到页面的控制层接口
     * @return 视图页面路径
     */
    @RequestMapping("/notfound")
    public String notFound() {
        // 实际跳转页面： /WEB-INF/pages/notfound.jsp
        return "/notfound";
    }
}
