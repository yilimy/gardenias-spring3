package com.example.boot3.bv1cn4y1o7q3.service;

/**
 * 用户服务
 * <p>
 *     没有无参构造器 (NoArgsConstructor) 的时候，spring在实例化bean的时候，会优先去容器中寻找有参构造器中的对象
 * 含有依赖的bean
 * @author caimeng
 * @date 2024/6/7 17:13
 */
//@NoArgsConstructor
public class UserService {
    private final OrderService orderService;

//    @Lazy
    public UserService(OrderService orderService) {
        /*
         * 注意，构造函数中的循环依赖是会报错的。
         * 有别于无参构造器 + Autowired 的方式，该方式spring通过三级缓存已经解决了。
         * 如果一定要在构造函数中使用循环依赖，可以在有参构造方法上使用懒加载 (@Lazy) 的方式
         *
         * 如果有多个有参构造函数，spring会报错，因为不知道通过哪个有参构造来创建对象。
         * 有无参构造函数不会报错，因为无参是默认调用，没有无参就会去找有参。
         * 当然，如果一定要在多个有参构造函数中进行构造，可以在xml文件中指定有参构造方法。
         */
        this.orderService = orderService;
    }

    public void test() {
        System.out.println("user : ... ");
        orderService.test();
    }
}
