package com.example.ssm.mvcb.action;

import com.example.ssm.mvcb.action.abs.AbstractAction;
import com.example.ssm.mvcb.vo.Emp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author caimeng
 * @date 2024/9/3 11:42
 */
@Slf4j
@Controller
@RequestMapping("/pages/emp/*")
public class EmpAction extends AbstractAction { // 父类存在有日期转换的支持

    /**
     * 浏览器访问： <a href="http://localhost/pages/emp/add_input" />
     * 跳转地址: {@link EmpAction#add(Emp)}
     */
    @GetMapping("/add_input")
    public String addInput() {
        return "/pages/emp/emp_add.jsp";
    }

    @PostMapping("/add")
    public ModelAndView add(Emp emp) { // 所有参数自动转成对象的成员属性
        log.info("【雇员信息】emp={}", emp);
        ModelAndView mav = new ModelAndView("/pages/emp/emp_add_show.jsp");
        mav.addObject("emp", emp);
        return mav;
    }
}
