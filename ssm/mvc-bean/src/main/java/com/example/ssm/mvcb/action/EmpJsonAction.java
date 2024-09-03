package com.example.ssm.mvcb.action;

import com.example.ssm.mvcb.action.abs.AbstractAction;
import com.example.ssm.mvcb.vo.Emp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author caimeng
 * @date 2024/9/3 11:42
 */
@Slf4j
@Controller
@RequestMapping("/pages/emp2/*")
public class EmpJsonAction extends AbstractAction {

    /**
     * 浏览器访问： <a href="http://localhost/pages/emp2/add_input" />
     * 跳转地址: {@link EmpJsonAction#add(Emp)}
     */
    @GetMapping("/add_input")
    public String addInput() {
        return "/pages/emp2/emp_add.jsp";
    }

    @PostMapping("/add")
    public ModelAndView add(@RequestBody Emp emp) { // RequestBody告诉控制层，此时传输的是一个完整的json数据
        log.info("【雇员信息】emp={}", emp);
        ModelAndView mav = new ModelAndView("/pages/emp2/emp_add_show.jsp");
        mav.addObject("emp", emp);
        return mav;
    }

    @PostMapping("/array")
    public ModelAndView array(@RequestBody List<Emp> emps) { // RequestBody告诉控制层，此时传输的是一个完整的json数据
        emps.forEach(System.out::println);
        return null;
    }
}
