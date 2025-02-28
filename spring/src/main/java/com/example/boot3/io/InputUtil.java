package com.example.boot3.io;

import cn.hutool.core.util.StrUtil;

import java.util.Scanner;

/**
 * @author caimeng
 * @date 2025/2/28 17:47
 */
public class InputUtil {

    public static String getString(String message) {
        System.out.print(message);
        while (true) {
            try {
                String ret = new Scanner(System.in).nextLine().trim();
                if (StrUtil.isBlank(ret)) {
                    System.out.print("输入错误，请重新输入：");
                } else {
                    return ret;
                }
            } catch (Exception e) {
                System.out.print("输入错误，请重新输入：");
            }
        }
    }
}
