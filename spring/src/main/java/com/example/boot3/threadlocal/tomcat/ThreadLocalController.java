package com.example.boot3.threadlocal.tomcat;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author caimeng
 * @date 2024/8/15 18:21
 */
@Slf4j
@RestController
@RequestMapping("/thread/local")
public class ThreadLocalController {
    /**
     * 待测试的复用 ThreadLocal
     */
    public static final ThreadLocal<String> LOCAL_MULTIPLEX = new ThreadLocal<>();

    /**
     * 请求后设置 ThreadLocal
     * @return string
     */
    @RequestMapping(value = "/multiplex")
    public String multiplex(){
        String local = LOCAL_MULTIPLEX.get();
        log.info("before = {}", local);
        if (ObjectUtils.isEmpty(local)) {
            LOCAL_MULTIPLEX.set("multiplex");
        }
        log.info("after = {}", LOCAL_MULTIPLEX.get());
        LOCAL_MULTIPLEX.remove();
        return "调用了服务";
    }
}
