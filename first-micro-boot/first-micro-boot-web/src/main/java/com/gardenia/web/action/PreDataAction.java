package com.gardenia.web.action;

import com.gardenia.web.advice.GlobalDataPreparedAdvice;
import com.gardenia.web.vo.PreDataCompany;
import com.gardenia.web.vo.PreDataDept;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据与处理的控制层
 * <a href="https://www.bilibili.com/video/BV1j9Fde5ELS/" />
 * @author caimeng
 * @date 2025/2/20 15:57
 */
@RestController
@RequestMapping("/preData/*")
public class PreDataAction {

    /**
     * 数据绑定预处理
     * <p>
     *     测试链接
     *     <a href="https://localhost/preData/add?cid=101&name=沐言科技&did=9090&name=教学研发部" />
     *     Company和Dept中有相同的属性名称:name
     *     结果:
     *          {
     *            "company": {
     *              "cid": 101,
     *              "name": "沐言科技,教学研发部"
     *            },
     *            "dept": {
     *              "did": 9090,
     *              "name": "沐言科技,教学研发部"
     *            }
     *          }
     *     最终执行的结果不重复的参数名称的属性被正常设置，
     *     但是重复的参数名称内容中间有个逗号，没有程序错误，但是不合预期。
     * <p>
     *     所有的请求数据，为了接收方便，都会通过HttpServletRequest对象中的getParameterMap()方法获取，
     *     接收到的内容都是数组，Spring调用了toString()进行了处理。
     * <p>
     *     如何修复上面的问题
     *     1. 配置数据绑定预处理 {@link GlobalDataPreparedAdvice}
     *     2. 添加 @ModelAttribute 注解修饰
     *     3. 请求路径中，同名的参数要加上对应的前缀
     *          <a href="https://localhost/preData/add?cid=101&company.name=沐言科技&did=9090&dept.name=教学研发部" />
     *
     * @param preDataCompany 含有name属性的 Company 类
     * @param preDataDept 含有name属性的 Dept 类
     * @return 响应数据
     */
    @RequestMapping("add")
    public Object dept(@ModelAttribute("company") PreDataCompany preDataCompany,
                       @ModelAttribute("dept")PreDataDept preDataDept){
        Map<String, Object> retMap = new HashMap<>();
        retMap.put("company", preDataCompany);
        retMap.put("dept", preDataDept);
        return retMap;
    }
}
