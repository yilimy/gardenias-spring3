package com.example.boot3.aop.dynamic.controller;

import cn.hutool.core.text.CharSequenceUtil;
import com.example.boot3.aop.dynamic.annotation.DynamicAOP;
import com.example.boot3.aop.dynamic.service.DynamicAopService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试动态操作aop的前端控制器
 * 测试脚本: resource/dynamic-aop.http
 * 环境变量: resource/http-client.private.env.json, dynamic-aop
 * @author caimeng
 * @date 2024/5/28 11:15
 */
@Slf4j
@RestController
@RequestMapping("/aop/dynamic")
public class DynamicAopController {
    @Autowired
    private DynamicAopService dynamicAopService;

    /**
     * 待测试方法
     * @return 方法返回值
     */
    @DynamicAOP
    @RequestMapping("/fun")
    public String fun() {
        return "原始数据";
    }

    /**
     * 添加一项动态aop拦截规则
     * @return 添加消息
     */
    @RequestMapping("/add")
    public String add(String interceptorClass, String expression, String annotationClass) {
        log.info("interceptorClass={}, expression={}, annotationClass={}", interceptorClass, expression, annotationClass);
        if (CharSequenceUtil.isAllBlank(expression, annotationClass) || CharSequenceUtil.isBlank(interceptorClass)) {
            return "The parameter is abnormal .";
        }
        dynamicAopService.add(interceptorClass, expression, annotationClass);
        return "添加了aop拦截";
    }

    /**
     * 删除一项动态拦截规则
     * @return 删除消息
     */
    @RequestMapping("/del")
    public String del(String interceptorClass, String expression, String annotationClass) {
        log.info("interceptorClass={}, expression={}, annotationClass={}", interceptorClass, expression, annotationClass);
        if (CharSequenceUtil.isAllBlank(expression, annotationClass) || CharSequenceUtil.isBlank(interceptorClass)) {
            return "The parameter is abnormal .";
        }
        dynamicAopService.del(interceptorClass, expression, annotationClass);
        return "删除了aop拦截";
    }
}
