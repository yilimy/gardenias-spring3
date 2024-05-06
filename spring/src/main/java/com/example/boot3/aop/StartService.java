package com.example.boot3.aop;

import cn.hutool.core.util.RandomUtil;
import com.example.boot3.aop.factory.ServiceFactory;
import com.example.boot3.aop.service.IDeptService;
import com.example.boot3.aop.service.impl.DeptServiceImpl;
import com.example.boot3.aop.vo.Dept;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * AOP编程属于面向切面的编程
 * 但是如果要理解的更简单一点，那么它就是一个动态代理机制的实习。
 * 动态代理的最大应用是在于事务的控制上。
 * @author caimeng
 * @date 2024/5/6 18:19
 */
@Slf4j
public class StartService {
    @SneakyThrows
    public static void main(String[] args) {

        IDeptService deptService = ServiceFactory.getInstance(DeptServiceImpl.class);
        Dept dept = new Dept()
                .setNo(RandomUtil.randomLong(100))
                .setName(RandomUtil.randomString(10))
                .setLoc(RandomUtil.randomString(15));
        /*
         * 同时整个流程中没有处理数据库的打开和关闭，只是一个简单的操作，就需要以上这么多代码
         * 而这些就是AOP技术发展的动机，
         * 因为不太规范，每个人可以按照个人的喜好编写代理的结构。
         * 所以如果想要规范化程序代码的开发，那么就需要进一步规范化动态代理的结构的设计，而这就是AOP产生的核心动机。
         * 利用AOP的技术概念对所有已掌握的核心技术进行二次的包装处理。
         * 除了可以使用JDK代理之外，还可以使用CgLib的代理机制，这一切都可以利用AOP直接进行控制的实现。
         */
        log.info("【部门数据增加】: {}", deptService.add(dept));
    }
}
