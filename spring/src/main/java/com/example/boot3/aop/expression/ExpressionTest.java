package com.example.boot3.aop.expression;

/**
 * AOP动态代理与原始动态代理最大的区别在于，所有的代理程序代码不再是通过硬编码实现了。
 * 这个时候主要是通过AspectJ表达式定义，相当于你现在只需要通过一个表达式就可以将代理与最终要执行的业务方法进行匹配了。
 * @author caimeng
 * @date 2024/5/8 18:16
 */
public class ExpressionTest {

    /**
     * 几个重要的通配符
     * <p>
     *     execution 定义通知的切点
     *     this 用于匹配当前AOP代理对象类型的执行方法
     *     target 用于匹配当前目标对象类型的执行方法
     *     args 用于匹配当前执行方法的传入参数
     * <p>
     *     execution 匹配格式
     *     注解匹配? 修饰符匹配? 方法返回值类型 操作类型匹配 方法名称匹配(参数匹配) 异常匹配?
     *     操作类型匹配 定义方法所在类的名称
     * <p>
     *     e.g.
     *     com.example..expression  两个点表示中间匹配多个包路径
     */
    public void wildcardTest() {

    }
}
