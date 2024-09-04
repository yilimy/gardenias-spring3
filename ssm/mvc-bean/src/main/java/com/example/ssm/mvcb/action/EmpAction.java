package com.example.ssm.mvcb.action;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.WeightRandom;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.RandomUtil;
import com.example.ssm.mvcb.action.abs.AbstractAction;
import com.example.ssm.mvcb.vo.Dept;
import com.example.ssm.mvcb.vo.Emp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    /**
     * <a href="http://localhost/pages/emp/get" />
     */
    @ResponseBody   // 直接进行json的响应
    @GetMapping("/get")
    public Object get() {
        return Emp.builder()
                .empno(7369L)
                .ename("李兴华")
                .hiredate(new Date())
                .updateTime(DateUtil.yesterday())
                .dept(Dept.builder()
                        .deptno(10L)
                        .dname("沐言科技教学研究部")
                        .loc("洛阳")
                        .build())
                .build();
    }

    /**
     * <a href="http://localhost/pages/emp/list" />
     */
    @ResponseBody   // 直接进行json的响应
    @GetMapping("/list")
    public Object list() {
        WeightRandom<String> nameRandom = RandomUtil.weightRandom(Arrays.asList(
                new WeightRandom.WeightObj<>("李兴华", 0.8d),
                new WeightRandom.WeightObj<>("李逍遥", 0.2d)
        ));
        WeightRandom<String> dNameRandom = RandomUtil.weightRandom(Arrays.asList(
                new WeightRandom.WeightObj<>("沐言科技教学研究部", 0.5d),
                new WeightRandom.WeightObj<>("沐言科技后勤保障部", 0.5d)
        ));
        WeightRandom<String> locRandom = RandomUtil.weightRandom(Arrays.asList(
                new WeightRandom.WeightObj<>("洛阳", 0.4d),
                new WeightRandom.WeightObj<>("长安", 0.6d)
        ));
        return Stream.generate(() -> Emp.builder()
                .empno(RandomUtil.randomLong(10000L))
                .ename(nameRandom.next())
                .hiredate(new Date())
                .dept(Dept.builder()
                        .deptno(RandomUtil.randomLong(1000L))
                        .dname(dNameRandom.next())
                        .loc(locRandom.next())
                        .build())
                .build()).limit(3).collect(Collectors.toList());
    }

    /**
     * <a href="http://localhost/pages/emp/map" />
     */
    @ResponseBody   // 直接进行json的响应
    @GetMapping("/map")
    public Object map() {
        return MapUtil.builder()
                .put("data", Emp.builder()
                        .empno(7369L)
                        .ename("李兴华")
                        .hiredate(new Date())
                        .dept(Dept.builder()
                                .deptno(10L)
                                .dname("沐言科技教学研究部")
                                .loc("洛阳")
                                .build())
                        .build())
                .put("skill", Set.of("Java", "Python", "Golang"))
                .build();
    }

}
