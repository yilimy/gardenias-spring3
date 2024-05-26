package com.example.boot3.base;

import cn.hutool.core.util.RandomUtil;
import org.junit.Test;

/**
 * java 标记
 * @author caimeng
 * @date 2024/5/26 15:37
 */
public class MarkTest {

    /**
     * 使用标记替代 if-else
     */
    @Test
    public void markIfElseTest() {
        int level_1 = 2;
        int level_2 = 4;
        int level_3 = 6;
        int level_4 = 8;
        int level_5 = 10;
        int cord = RandomUtil.randomInt(1, 10);
        int score = 0;
        // 标记
        exam:
        if (RandomUtil.randomBoolean()) {
            if (cord < level_1) {
                System.out.printf("Failed , cord=%s\n", cord);
                score = cord;
                // 已经拿到结果，不需要往下在执行了
                break exam;
            }
            if (cord < level_2) {
                System.out.printf("Congratulations to an admission, level_2 , cord=%s\n", cord);
                score = cord;
                break exam;
            }
            if (cord < level_3) {
                System.out.printf("Congratulations to an admission, level_3 , cord=%s\n", cord);
                score = cord;
                break exam;
            }
            if (cord < level_4) {
                System.out.printf("Congratulations to an admission, level_4 , cord=%s\n", cord);
                score = cord;
                break exam;
            }
            if (cord < level_5) {
                System.out.printf("Congratulations to an admission, level_5 , cord=%s\n", cord);
                score = cord;
                break exam;
            }
            System.out.printf("作弊 , cord=%s\n", cord);
        } else {
            System.out.println("exam undue .");
        }
        System.out.printf("finally score=%s\n", score);
    }

    /**
     * 使用标记，跳出循环
     */
    @Test
    public void outOfCycle() {
        int i = RandomUtil.randomInt(5);
        System.out.println("init i=" + i);
        // 标记位
        middle:
        while (true) {
            switch (i) {
                case 1:
                    System.out.println("i=" + i);
                    i ++;
                    // 普通break，跳出switch
                    break;
                case 2:
                    System.out.println("i=" + i);
                    i = i + 2;
                    break;
                case 3:
                    System.out.println("i=" + i);
                    // 标记break，跳出到标记位
                    break middle;
                case 4:
                    System.out.println("i=" + i);
                    i --;
                    break;
                case 5:
                    System.out.println("i=" + i);
                    i = i - 4;
                    break;
                default:
                    System.out.println("没有捕获的数据，i=" + i);
                    break middle;
            }
        }
        System.out.println("over, i=" + i);
    }
}
