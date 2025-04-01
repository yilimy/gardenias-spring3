package com.gardenia.redis.lettuce;

import com.gardenia.redis.lettuce.util.RedisConnectionPoolUtil;
import io.lettuce.core.GeoArgs;
import io.lettuce.core.Limit;
import io.lettuce.core.Range;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author caimeng
 * @date 2025/3/31 17:28
 */
@Slf4j
public class RedisAsyncCommandTest {

    @SneakyThrows
    @Test
    public void hashTest() {
        try (StatefulRedisConnection<String, String> connection = RedisConnectionPoolUtil.getConnection()) {
            RedisAsyncCommands<String, String> commands = connection.async();
            // 清空数据库
//            commands.flushdb();
            // 定义待存储的数据
            Map<String, String> map = new HashMap<>();
            map.put("name", "爆可爱的小李老师");
            map.put("corp", "yootk.com");
            map.put("book", "《redis开发实战》");
            log.debug("【Hash数据】设置一组hash数据: {}", map);
            String key = "member:yootk";
            commands.hset(key, map).thenAccept(result -> {
                // 【Hash数据】设置hash数据结果: 3
                log.info("【Hash数据】设置hash数据结果: {}", result);
            });
            log.info("【Hash数据】姓名：{}", commands.hget(key, "name").get());
            log.info("【Hash数据】机构：{}", commands.hget(key, "corp").get());
            log.info("【Hash数据】图书：{}", commands.hget(key, "book").get());
        }
    }

    @SneakyThrows
    @Test
    public void listTest() {
        try (StatefulRedisConnection<String, String> connection = RedisConnectionPoolUtil.getConnection()) {
            RedisAsyncCommands<String, String> commands = connection.async();
            String key = "books:yootk";
            // 清楚旧数据
            commands.del(key);
            // 左边队列压入
            commands.lpush(key, "Java", "JVM", "spring");
            // 右边队列压入
            commands.rpush(key, "Redis", "Netty");
            // 获取全部数据
            commands.lrange(key, 0, -1).thenAccept(result -> {
                // 【List数据】队列数据：[Java, JVM, spring, Redis, Netty]
                log.info("【List数据】队列数据：{}", result);
            });
            // 集合数据左端弹出
            commands.lpop(key).thenAccept(result -> {
                // 【List数据】集合数据左端弹出：spring
                log.info("【List数据】集合数据左端弹出：{}", result);
            });
            // 集合数据右端弹出
            commands.rpop(key).thenAccept(result -> {
                // 【List数据】集合数据右端弹出：Netty
                log.info("【List数据】集合数据右端弹出：{}", result);
            });
            // 确保异步任务执行完毕
            TimeUnit.SECONDS.sleep(5);
        }
    }

    /**
     * set的优势在于:
     * 1. 去重
     * 2. 计算交集 | 并集 | 差集
     */
    @SneakyThrows
    @Test
    public void setTest() {
        try (StatefulRedisConnection<String, String> connection = RedisConnectionPoolUtil.getConnection()) {
            RedisAsyncCommands<String, String> commands = connection.async();
            String key1 = "skill:muyan";
            String key2 = "skill:yootk";
            // 清楚旧数据
            commands.del(key1, key2);
            commands.sadd(key1, "Java", "HTML", "python");
            commands.sadd(key2, "Java", "golang", "python");
            // --------------------- 获取数据长度 ---------------------
            // 【Set数据】key1集合数据个数：3
            commands.scard(key1).thenAccept(result -> log.info("【Set数据】key1集合数据个数：{}", result));
            // 【Set数据】key2集合数据个数：3
            commands.scard(key2).thenAccept(result -> log.info("【Set数据】key2集合数据个数：{}", result));
            // --------------------- 交集、差集、并集 ---------------------
            // 【Set数据】交集：[Java, python]
            commands.sinter(key1, key2).thenAccept(result -> log.info("【Set数据】交集：{}", result));
            // 【Set数据】差集：[HTML]
            commands.sdiff(key1, key2).thenAccept(result -> log.info("【Set数据】差集：{}", result));
            // 【Set数据】并集：[Java, HTML, python, golang]
            commands.sunion(key1, key2).thenAccept(result -> log.info("【Set数据】并集：{}", result));
            // 确保异步任务执行完毕
            TimeUnit.SECONDS.sleep(5);
        }
    }

    /**
     * 数据排序处理
     * <p>
     *     热数据 ZSet
     */
    @SneakyThrows
    @Test
    public void sortedSetTest() {
        try (StatefulRedisConnection<String, String> connection = RedisConnectionPoolUtil.getConnection()) {
            RedisAsyncCommands<String, String> commands = connection.async();
            String key = "hotkey:919";
            commands.del(key);
            commands.zadd(key, 8.0, "Java").thenAccept(result -> {
                // 【ZSet数据】添加Java数据：1
                log.info("【ZSet数据】添加Java数据：{}", result);
            });
            commands.zadd(key, 3.0, "Python").thenAccept(result -> {
                // 【ZSet数据】添加Python数据：1
                log.info("【ZSet数据】添加Python数据：{}", result);
            });
            // 一般在获取热点数据的时候，需要获取部分数据（比如：前10条），所以需要设置起始位置和结束位置
            // 从高到低排序
            commands.zrevrangebyscoreWithScores(
                    key,
                    // 分值在 1.0 - 9.0 之间
                    Range.create(1.0, 9.0),
                    // 获取前 1 条数据, 偏移量: 0
                    Limit.create(0, 1)).thenAccept(result -> {
                        // 【ZSet数据】获取前1条数据：[ScoredValue[8.000000, Java]]
                        log.info("【ZSet数据】获取前1条数据：{}", result);
                    });
            // 确保异步任务执行完毕
            TimeUnit.SECONDS.sleep(5);
        }
    }

    /**
     * 测试: 位操作
     */
    @SneakyThrows
    @Test
    public void bitTest() {
        try (StatefulRedisConnection<String, String> connection = RedisConnectionPoolUtil.getConnection()) {
            RedisAsyncCommands<String, String> commands = connection.async();
            String key = "clockin:yootk";
            commands.del(key);
            // --------------------- 设置位数据 ---------------------
            // 【位操作】设置位数据：0，返回的是该位置中已存在数据的值，没有就返回0
            commands.setbit(key, 3, 1).thenAccept(result -> log.info("【位操作】设置位数据：{}", result));
            // 【位操作】设置位数据：0
            commands.setbit(key, 5, 1).thenAccept(result -> log.info("【位操作】设置位数据：{}", result));
            // --------------------- 获取位数据 ---------------------
            // 【位操作】获取位数据：1
            commands.getbit(key, 3).thenAccept(result -> log.info("【位操作】获取位数据：{}", result));
            // 【位操作】获取位数据：1
            commands.getbit(key, 5).thenAccept(result -> log.info("【位操作】获取位数据：{}", result));
            // 确保异步任务执行完毕
            TimeUnit.SECONDS.sleep(5);
        }
    }

    /**
     * 测试: HyperLogLog
     * <p>
     *     统计基数
     */
    @SneakyThrows
    @Test
    public void hyperLogLogTest() {
        try (StatefulRedisConnection<String, String> connection = RedisConnectionPoolUtil.getConnection()) {
            RedisAsyncCommands<String, String> commands = connection.async();
            String key1 = "module:program";
            String key2 = "module:book";
            commands.del(key1, key2);
            // --------------------- 设置HyperLogLog数据 ---------------------
            commands.pfadd(key1, "Java", "Python", "JavaScript", "C++", "Java").thenAccept(result -> {
                // 【HyperLogLog】统计基数：1
                log.info("【HyperLogLog】统计基数：{}", result);
            });
            commands.pfadd(key2, "Java", "Python", "JavaScript", "C#").thenAccept(result -> {
                // 【HyperLogLog】统计基数：1
                log.info("【HyperLogLog】统计基数：{}", result);
            });
            // --------------------- 统计基数 ---------------------
            // 【HyperLogLog】统计基数k1：4
            commands.pfcount(key1).thenAccept(result -> log.info("【HyperLogLog】统计基数k1：{}", result));
            // 【HyperLogLog】统计基数k2：4
            commands.pfcount(key2).thenAccept(result -> log.info("【HyperLogLog】统计基数k2：{}", result));
            // 【HyperLogLog】统计基数k12：5
            commands.pfcount(key1, key2).thenAccept(result -> log.info("【HyperLogLog】统计基数k12：{}", result));
            // 确保异步任务执行完毕
            TimeUnit.SECONDS.sleep(5);
        }
    }

    /**
     * 测试: 地理位置
     */
    @SneakyThrows
    @Test
    public void geoTest() {
        try (StatefulRedisConnection<String, String> connection = RedisConnectionPoolUtil.getConnection()) {
            RedisAsyncCommands<String, String> commands = connection.async();
            String key = "point";
            commands.del(key);
            // 城楼
            Point2D CL = new Point2D.Float(116.403963f, 39.915119f);
            // 王府井
            Point2D WFJ = new Point2D.Float(116.417876f, 39.915411f);
            // 前门
            Point2D QM = new Point2D.Float(116.404354f,39.904748f);
            // 当前
            Point2D local = new Point2D.Float(116.415901f,39.914805f);
            // --------------------- 设置 GEO 数据 ---------------------
            commands.geoadd(key, CL.getX(), CL.getY(),  "城楼").thenAccept(result -> log.info("【GEO】设置 城楼 数据：{}", result));
            commands.geoadd(key, WFJ.getX(), WFJ.getY(),  "王府井").thenAccept(result -> log.info("【GEO】设置 王府井 数据：{}", result));
            commands.geoadd(key, QM.getX(), QM.getY(),  "前门").thenAccept(result -> log.info("【GEO】设置 前门 数据：{}", result));
            commands.geoadd(key, local.getX(), local.getY(),  "当前").thenAccept(result -> log.info("【GEO】设置 当前 数据：{}", result));
            // --------------------- GEO 计算 ---------------------
            // 【GEO】计算：城楼与王府井的距离：1187.5637
            commands.geodist(key, "城楼", "王府井", GeoArgs.Unit.m).thenAccept(result -> log.info("【GEO】计算：城楼与王府井的距离：{}", result));
            // 确保异步任务执行完毕
            TimeUnit.SECONDS.sleep(5);
        }
    }

    /**
     * 测试: 根据 Keys 排查
     */
    @SneakyThrows
    @Test
    public void keysTest() {
        try (StatefulRedisConnection<String, String> connection = RedisConnectionPoolUtil.getConnection()) {
            RedisAsyncCommands<String, String> commands = connection.async();
            // --------------------- 设置普通数据 ---------------------
            log.info("【字符串数据】追加普通数据: {}", commands.set("yootk", "yootk.com").get());
            log.info("【Hash数据】追加Hash数据: {}", commands.hset("member2:yootk", "name", "李兴华").get());
            log.info("【List数据】追加List数据: {}", commands.lpush("message:yootk", "hello", "nice", "good").get());
            // --------------------- 数据查询 ---------------------
            List<String> strings = commands.keys("*").get();
            strings.forEach(k -> {
                try {
                    log.info("【数据项】key={}, type={}", k, commands.type(k).get());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            // 确保异步任务执行完毕
            TimeUnit.SECONDS.sleep(5);
        }
    }
}
