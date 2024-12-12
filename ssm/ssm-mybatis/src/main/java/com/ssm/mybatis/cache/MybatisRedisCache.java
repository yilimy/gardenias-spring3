package com.ssm.mybatis.cache;

import com.ssm.mybatis.redis.RedisConnectionUtil;
import io.lettuce.core.api.StatefulRedisConnection;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.Cache;
import org.springframework.util.SerializationUtils;

/**
 *
 * @author caimeng
 * @date 2024/12/11 17:55
 */
@Slf4j
public class MybatisRedisCache implements Cache {
    /**
     * 获取redis的连接
     */
    protected final StatefulRedisConnection<byte[], byte[]> connection = RedisConnectionUtil.getConnection();
    /**
     * 定义一个缓存的操作ID
     */
    private final String operateId;

    public MybatisRedisCache(String operateId) {
        // 【设置缓存ID】id = com.ssm.mybatis.mapper.BookMapper
        log.debug("【设置缓存ID】id = {}", operateId);
        this.operateId = operateId;
    }

    @Override
    public String getId() {
        // 【获取缓存ID】id = com.ssm.mybatis.mapper.BookMapper
        log.debug("【获取缓存ID】id = {}", this.operateId);
        return this.operateId;
    }

    @Override
    public void putObject(Object key, Object value) {
        log.debug("【缓存数据的写入】key = {}, value = {}", key, value);
        // 使用同步操作，进行数据设置，需要对kv进行序列化(因为用的是字节来操作)
        this.connection.sync().set(SerializationUtils.serialize(key), SerializationUtils.serialize(value));
    }

    @Override
    public Object getObject(Object key) {
        byte[] data = this.connection.sync().get(SerializationUtils.serialize(key));
        if (data == null) {
            // 【缓存数据读取为空】key = -1682489330:1575414670:com.ssm.mybatis.mapper.BookMapper.findById:0:2147483647:select bid, title, author, price from book where bid=?:3:development
            log.debug("【缓存数据读取为空】key = {}", key);
            return null;
        }
        /*
         * 方法将在未来（Spring Framework 6.0）被弃用，因为它使用了Java对象序列化机制，这允许任意代码执行，
         * 并且已知是许多远程代码执行（RCE）漏洞的源头。
         *
         * 官方建议使用外部工具进行序列化和反序列化，比如那些序列化到JSON、XML或任何其他格式的工具，
         * 这些工具会定期检查和更新，以防止RCE漏洞。
         */
        @SuppressWarnings("deprecation")
        Object value = SerializationUtils.deserialize(data);
        log.debug("【缓存数据读取】key = {}, value = {}", key, value);
        return value;
    }

    @Override
    public Object removeObject(Object key) {
        log.debug("【删除缓存数据】key = {}", key);
        return this.connection.sync().del(SerializationUtils.serialize(key));
    }

    @Override
    public void clear() {
        log.debug("【清除缓存数据】");
        // flushdb 清除本DB，flushall清除所有数据
        this.connection.sync().flushdb();
    }

    @Override
    public int getSize() {
        int size = this.connection.sync().dbsize().intValue();
        log.debug("【获取缓存的个数】size = {}", size);
        return size;
    }
}
