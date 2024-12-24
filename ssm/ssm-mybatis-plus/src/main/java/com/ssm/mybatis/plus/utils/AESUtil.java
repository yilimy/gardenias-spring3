package com.ssm.mybatis.plus.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * AES加密工具
 * @author caimeng
 * @date 2024/12/24 17:42
 */
public class AESUtil {
    /**
     * 密钥长度：16位
     */
    public static final String KEY = "mybatis-plus.com";
    public static final String CHARSET = "UTF-8";
    public static final int OFFSET = 16;
    public static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";
    public static final String ALGORITHM = "AES";

    public static String encrypt(String content) {
        return encrypt(content, KEY);
    }

    public static String decrypt(String content) {
        return decrypt(content, KEY);
    }

    /**
     * 加密
     * @param content 待加密明文
     * @param key 加密因子
     * @return 密文
     */
    public static String encrypt(String content, String key) {
        try {
            byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec sKey = new SecretKeySpec(keyBytes, ALGORITHM);
            IvParameterSpec iv = new IvParameterSpec(keyBytes, 0, OFFSET);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            byte[] contentBytes = content.getBytes(CHARSET);
            cipher.init(Cipher.ENCRYPT_MODE, sKey, iv);
            byte[] result = cipher.doFinal(contentBytes);
            return Base64.getEncoder().encodeToString(result);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 解密
     * @param content 待解密密文
     * @param key 加密因子
     * @return 原文
     */
    public static String decrypt(String content, String key) {
        try {
            byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec sKey = new SecretKeySpec(keyBytes, ALGORITHM);
            IvParameterSpec iv = new IvParameterSpec(keyBytes, 0, OFFSET);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            byte[] contentBytes = Base64.getDecoder().decode(content);
            cipher.init(Cipher.DECRYPT_MODE, sKey, iv);
            byte[] result = cipher.doFinal(contentBytes);
            return new String(result, CHARSET);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
