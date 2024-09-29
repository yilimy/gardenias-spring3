package com.example.ssm.security;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 测试数据加密
 * @author caimeng
 * @date 2024/9/29 18:57
 */
public class UserTest {
    @Test
    public void createSecurityPasswordTest() {
        // 定义加密算法
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String plain = "hello";
        String cipherText = encoder.encode(plain);
        /*
         * $2a$10$ncT6ccP.CoVlum8W5f8H8uG5sxejkxxxOZlk6twbwhvDCnqW1w/5q
         * 注意：每次加密后的结果值不相同
         */
        System.out.println("加密后的密码:" + cipherText);
    }
}
