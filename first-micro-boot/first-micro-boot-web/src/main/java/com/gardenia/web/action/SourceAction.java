package com.gardenia.web.action;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 实现资源的注入
 * @author caimeng
 * @date 2025/1/24 16:29
 */
@RequestMapping("/source/*")
@RestController
public class SourceAction {
    @Value("${source.mysql:}")
    private String mysql;
    @Value("${source.redis:}")
    private String redis;
    @Value("${source.messages:}")
    private List<String> messages;
    /*
     * 这里的${source.infos:}没有被包裹在单引号中，是从配置文件中读取值
     * 反之，如果被包裹在单引号中，会被SpEL解析为字符串字面量
     * 如: @Value("#{'${source.messages:}' ?: new java.util.ArrayList()}")
     * <p>
     *      在定义Map集合的时候采用的是SpEL表达式对给定的字符串进行处理，
     *      相当于将 @Value("#{...key...}") => @Value(obj), 将SpEL解析结果直接赋值，
     *      配置文件中的key只在SpEL阶段生效，在@Value阶段就跟配置文件没关系了。
     * <p>
     *      一般的@Value使用的是properties文件中的key，进行值读取
     */
    @Value("#{${source.infos:} ?: new java.util.HashMap()}")   // 通过SpEL表达式来进行数据处理
    private Map<String, String> infos;

    /**
     * 测试数据注入
     * @return 结果
     */
    @GetMapping("show")
    public Object show() {
        record Ret(String mysql, String redis, List<String> messages, Map<String, String> infos){}
        return new Ret(mysql, redis, messages, infos);
    }
}
