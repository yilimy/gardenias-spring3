package com.example.ssm.mvcb.action;

import cn.hutool.core.map.MapUtil;
import com.example.ssm.mvcb.service.IAdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 该action用来测试注解在业务上的权限校验
 * @author caimeng
 * @date 2024/10/14 10:24
 */
@Slf4j
@Controller
@RequestMapping("/pages/admin")     // 父路径(/pages/admin)没有进行安全检查
public class AdminAction {
    @Autowired
    private IAdminService iAdminService;

    /**
     * 访问地址 <a href="http://localhost/pages/admin/add" />
     * @return 返回结果
     */
    @GetMapping("/add")
    @ResponseBody
    public Object add(){
        return MapUtil.builder()
                .put("result", "创建新的管理员")
                .put("flag", iAdminService.add())
                .build();
    }

    /**
     * 访问地址 <a href="http://localhost/pages/admin/edit" />
     * @return 返回结果
     */
    @GetMapping("/edit")
    @ResponseBody
    public Object edit(){
        return MapUtil.builder()
                .put("result", "修改管理员数据")
                .put("flag", iAdminService.edite())
                .build();
    }

    /**
     * 访问地址 <a href="http://localhost/pages/admin/delete?ids=muyan&ids=yootk&ids=lee" />
     * @return 返回结果
     */
    @GetMapping("/delete")
    @ResponseBody
    public Object delete(@RequestParam("ids") List<String> ids){
        log.info("ids={}", ids);
        return MapUtil.builder()
                .put("result", "修改管理员数据")
                .put("flag", iAdminService.delete(ids))
                .build();
    }

    /**
     * 访问地址 <a href="http://localhost/pages/admin/get?username=yootk" />
     * @param username 用户名
     * @return 结果
     */
    @GetMapping("/get")
    @ResponseBody
    public Object get(String username){
        log.info("get username={}", username);
        return Map.of("result", iAdminService.get(username));
    }

    /**
     * 测试JSR-250
     * 访问地址 <a href="http://localhost/pages/admin/list" />
     * @return 结果
     */
    @GetMapping("/list")
    @ResponseBody
    public Object list(){
        return Map.of("result", iAdminService.list());
    }
}
