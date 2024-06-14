package org.example.mock.service.beans;

/**
 * 测试：一个类被多个切面代理
 * 相关功能没有实现
 * @author caimeng
 * @date 2024/6/14 16:33
 */
//@IComponent
public class AopComplexService {
    public void test() {
        System.out.println(AopSimpleService.class.getSimpleName() + " test ...");
    }
}
