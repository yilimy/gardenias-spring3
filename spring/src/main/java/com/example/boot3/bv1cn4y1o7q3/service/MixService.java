package com.example.boot3.bv1cn4y1o7q3.service;

import java.util.Objects;

/**
 * 混合bean
 * @author caimeng
 * @date 2024/6/7 17:58
 */
public class MixService {
    private OrderService orderService;
    private final UserService userService;

    /**
     * 有参构造器1
     * @param userService 依赖bean
     */
    public MixService(UserService userService) {
        this.userService = userService;
    }

    /**
     * 有参构造器2
     * 存在多个构造器 {@link MixService#MixService(UserService)}
     * 构造器中混合了多个依赖bean
     * @param orderService 依赖bean1
     * @param userService 依赖bean2
     */
    public MixService(OrderService orderService, UserService userService) {
        this(userService);
        this.orderService = orderService;
    }

    public void test() {
        System.out.println("mix : xxx ");
        if (Objects.nonNull(orderService)) {
            System.out.println("orderService exists .");
        }
        userService.test();
    }
}
