package com.example.dubbo.provider.integration;

import com.alibaba.dubbo.config.annotation.Service;
import com.example.dubbo.inter.DemoService;

/**
 * @author caimeng
 * @date 2024/4/18 15:33
 */
@Service
public class DemoServiceImpl implements DemoService {
    @Override
    public String sayHello(String name) {
        return "Provider: May all the beauty be blessed !";
    }
}
