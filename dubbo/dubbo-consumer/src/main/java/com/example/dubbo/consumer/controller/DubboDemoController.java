package com.example.dubbo.consumer.controller;

import com.example.dubbo.consumer.service.DubboDemoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author caimeng
 * @date 2024/5/15 18:37
 */
@Slf4j
@RestController
public class DubboDemoController {
    @Autowired
    private DubboDemoService dubboDemoService;

    @RequestMapping("/initOrder")
    public String initOrder(String userId) {
        log.info("initOrder: {}", userId);
        dubboDemoService.initOrder(userId);
        return "订单成功, " + UUID.randomUUID();
    }
}
