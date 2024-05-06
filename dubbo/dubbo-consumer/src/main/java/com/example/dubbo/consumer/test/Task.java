package com.example.dubbo.consumer.test;

import cn.hutool.core.util.RandomUtil;
import com.example.dubbo.inter.service.UserService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author caimeng
 * @date 2024/4/29 14:10
 */
@Component
public class Task implements CommandLineRunner {
    @DubboReference
    private UserService userService;

    @Override
    public void run(String... args) throws Exception {
        userService.getUserAddressList(RandomUtil.randomString(8));
    }
}
