package com.gardenia.web.action;

import com.gardenia.web.vo.Dept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author caimeng
 * @date 2025/2/6 10:05
 */
@RestController
@RequestMapping("/dept/*")
public class DeptAction {
    @Autowired
    private Dept dept;
    @RequestMapping("get")
    public Object get() {
        return dept;
    }
}
