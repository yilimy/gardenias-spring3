package com.ssm.mybatis.cache;

import io.lettuce.core.SetArgs;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.Duration;

/**
 * 自定义序列化缓存实现
 * @author caimeng
 * @date 2024/12/11 17:55
 */
@Slf4j
public class MybatisRedisSerializeDefinedCache extends MybatisRedisCache {

    public MybatisRedisSerializeDefinedCache(String operateId) {
        super(operateId);
    }

    @Override
    public void putObject(Object key, Object value) {
        log.debug("【缓存数据的写入】key = {}, value = {}", key, value);
        this.connection.sync().set(
                SerializationUtils.serialize(key),
                SerializationUtils.serialize(value),
                // 设置缓存有效期 (TTL)
                SetArgs.Builder.ex(Duration.ofSeconds(300))
        );
    }

    @Override
    public Object getObject(Object key) {
        byte[] data = this.connection.sync().get(SerializationUtils.serialize(key));
        if (data == null) {
            log.debug("【缓存数据读取为空】key = {}", key);
            return null;
        }
        Object value = SerializationUtils.deserialize(data);
        log.debug("【缓存数据读取】key = {}, value = {}", key, value);
        return value;
    }

    @Override
    public Object removeObject(Object key) {
        log.debug("【删除缓存数据】key = {}", key);
        return this.connection.sync().del(SerializationUtils.serialize(key));
    }

    /**
     * 自定义序列化工具类
     */
    private static class SerializationUtils{
        // 构造函数私有化
        private SerializationUtils(){}

        /**
         * 序列化接口
         * <p>
         *     也是spring提供的序列化方法
         *     {@link org.springframework.util.SerializationUtils}
         *     但是该反序列化接口被认为有安全漏洞，建议变更序列化方案
         * @param object 待序列化对象，需要实现 {@link Serializable} 接口
         * @return 序列化结果
         */
        public static byte[] serialize(Object object) {
            try (ByteArrayOutputStream bao = new ByteArrayOutputStream();   // 内存输出流
                 // 内存流包装成对象流
                 ObjectOutputStream oos = new ObjectOutputStream(bao)       // 对象输出流
            ) {
                // 对象写入对象流
                oos.writeObject(object);
                oos.flush();
                // 获取对象的序列化二进制数据
                return bao.toByteArray();
            } catch (Exception e) {
                log.error("序列化失败", e);
            }
            return null;
        }

        /**
         * 反序列化接口
         * @param bytes 反序列化值
         * @return 反序列化后得到的对象
         */
        public static Object deserialize(byte[] bytes) {
            try (ByteArrayInputStream bai = new ByteArrayInputStream(bytes);
                 ObjectInputStream ois = new ObjectInputStream(bai)) {
                return ois.readObject();
            } catch (Exception e) {
                log.error("序列化失败", e);
            }
            return null;
        }
    }
}
