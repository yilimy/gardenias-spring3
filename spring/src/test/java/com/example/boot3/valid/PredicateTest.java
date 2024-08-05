package com.example.boot3.valid;

import org.junit.jupiter.api.Test;

import java.util.function.Predicate;

/**
 * @author caimeng
 * @date 2024/8/2 16:02
 */
public class PredicateTest {

    /**
     * 测试 Predicate OR 和 AND 的范围
     */
    @Test
    public void orAndTest() {
        Predicate<Integer> pd1 = i -> i > 20;
        Predicate<Integer> pd2 = i -> i < 10;
        Predicate<Integer> pd3 = i -> i % 2 == 0;
        assert pd1.or(pd2).and(pd3).test(8) : "验证失败";
        assert pd1.or(pd2).and(pd3).test(22) : "验证失败";
        assert !pd1.or(pd2).and(pd3).test(12) : "验证失败";
        assert !pd1.or(pd2).and(pd3).test(9) : "验证失败";
        assert !pd1.or(pd2).and(pd3).test(21) : "验证失败";
        /*
         * 注意： and优先级高于or
         * 但是使用 Predicate 的 OR 和 AND 方法时，并不是先将这两个方法预解释为逻辑运算符作为整体的表达式处理，而是单纯的方法调用。
         * 因为没有将整个链路解释成逻辑运算符，因此不会继承逻辑运算符先后顺序，而是遵从于方法的从左向右调用的顺序。
         *      - 括号仍然有效
         *      - 逻辑短路仍然有效
         * 在链路中的子节点内部，依然遵从于逻辑运算的优先级。
         */
        assert pd1.or(pd2).and(pd3).test(8) : "验证失败";
        /*
         * pd1==1, pd3==0, 按逻辑运算的and优先级高于or，优先执行and，再因为pd1==1返回true，断路，但是结果不是这样.
         * 说明 Predicate 没有将链路解释成表达式: !(or + and)
         * 而是按照方法执行的顺序执行: or -> and -> () -> !
         */
        assert !(pd1.or(pd2).and(pd3)).test(21) : "验证失败";
    }
}
