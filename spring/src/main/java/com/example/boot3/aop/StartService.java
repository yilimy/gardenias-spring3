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
 * <p>
 *     学习技术的要点
 *     1. 要清楚这个技术解决了哪些问题
 *     2. 要理解这个技术的核心思想
 *     3. 要理解这个技术的具体实现（应用与内涵）
 * </p>
 * <p>
 *     AOP的概念
 *     AOP本质上是一种编程范式，提供从另一个角度来考虑程序结构从而完善面向对象编程（OOP）模型。
 *     AOP是基于动态代理的一种更高级的应用，其主要的目的是可以结合Aspect组件，利用切面表达式将代理类织入到程序组成中，以实现组建的解耦合设计。
 *     AOP主要用于横切关注点的分离和织入，因此在AOP处理之中需要关注以下几个核心概念：
 *      1. 关注点： 可以认为是所关注的任何东西。例如：业务接口、支付处理、消息发送处理等；
 *      2. 关注点分离： 将业务逐步拆分后形成一个个不可拆分的独立组件
 *      3. 横切关注点： 实现代理功能，利用代理功能可以将辅助操作在多个关注点上执行。横切点可能包括多种：事务、日志、角色、鉴权、性能等
 *      4. 织入： 将横切关注点分离后，可能需要确定关注点的执行位置，有可能在业务方法之前，有可能在调用之后，或者是产生异常时
 *     之所以能使用Spring之中的AOP技术进行配置的处理，主要是由于AspectJ的支持，该技术是apache的一个古早技术，这个技术可以实现切面表达式的动态代理。
 *     可以直接利用方法的切面表达式来决定哪些方法要执行AOP的动态代理控制，如果要修改的时候，只要修改一个表达式即可。
 * </p>
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
