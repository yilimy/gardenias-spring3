package com.ssm.mybatis.plus.utils;

import org.junit.jupiter.api.Test;

/**
 * 测试：AES工具
 * @author caimeng
 * @date 2024/12/24 17:58
 */
public class AESUtilTest {

    @Test
    public void encryptTest() {
        // MMlNql4pi7osNhFedtZ3XPqYdK1w3QD2SrQfvPRU1ufIc76aoxcOGPXHNXn4Juqatrd1cC1KG23rnSZ7TI1sO5SxagsdzYXQHsThy83zuNkPfjEgCtXzNXol99nhPrn1P0pphK2jmnRtRr2QXJBkwnsCYd+z8AzS0Gejy4ZIbbUgYrDrKqzO9wotmxaSxeoLocygtacNPrL85wrGqoiGEQ==
        String jdbcUrl = "jdbc:mysql://192.168.200.130:3306/test_sql?useUnicode=true&allowMultiQuerie=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&useSSL=false";
        System.out.println("JDBC连接地址: " + AESUtil.encrypt(jdbcUrl));
        // ucmD4klBqsLfYwiEfZWJkg==
        String username = "root";
        System.out.println("用户名:       " + AESUtil.encrypt(username));
        // QLBfOeQV7cN5ruVWl+cnhg==
        String password = "Gm02_prd8!";
        System.out.println("用户密码:     " + AESUtil.encrypt(password));
    }

    @Test
    public void decryptTest() {
        System.out.println("JDBC连接地址：" + AESUtil.decrypt("MMlNql4pi7osNhFedtZ3XPqYdK1w3QD2SrQfvPRU1ufIc76aoxcOGPXHNXn4Juqatrd1cC1KG23rnSZ7TI1sO5SxagsdzYXQHsThy83zuNkPfjEgCtXzNXol99nhPrn1P0pphK2jmnRtRr2QXJBkwnsCYd+z8AzS0Gejy4ZIbbUgYrDrKqzO9wotmxaSxeoLocygtacNPrL85wrGqoiGEQ=="));
        System.out.println("用户名：    ：" + AESUtil.decrypt("ucmD4klBqsLfYwiEfZWJkg=="));
        System.out.println("用户密码：    " + AESUtil.decrypt("QLBfOeQV7cN5ruVWl+cnhg=="));
    }
}
