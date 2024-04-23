package com.example.dubbo.provider.integration;

import com.example.dubbo.inter.DemoService;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @author caimeng
 * @date 2024/4/18 15:33
 */
@DubboService
public class DemoServiceImpl implements DemoService {
    @Override
    public String sayHello(String name) {
        return "Provider: May all the beauty be blessed !";
    }
}
