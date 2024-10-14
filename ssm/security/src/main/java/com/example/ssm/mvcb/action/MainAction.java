package com.example.ssm.mvcb.action;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author caimeng
 * @date 2024/10/14 15:55
 */
@Slf4j
@Controller
public class MainAction {

    /**
     * 标签测试
     * <p>
     *     匿名访问
     *          <a href="http://localhost/main" />
     *          报错 ”Invalid property 'principal.username' of bean class“
     *          实际上此时的 principal=anonymousUser
     *          通过 <a href="http://localhost/pages/member/info" /> 可以查看
     * <p>
     *     登录用户访问
     *          先登录 <a href="http://localhost/login" />  yootk / hello
     *          再访问 <a href="http://localhost/main" />
     *     成功展示 main.jsp 页面
     *
     * @see com.example.ssm.mvcb.config.ResourceViewConfig#resourceViewResolver()
     * @return 跳转页面 /pages/main.jsp
     */
    @SuppressWarnings({"ConfusingMainMethod", "SpringMVCViewInspection"})
    @GetMapping("/main")
    public Object main(){
        return "/main";
    }
}
