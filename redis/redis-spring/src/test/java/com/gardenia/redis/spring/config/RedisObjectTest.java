package com.gardenia.redis.spring.config;

import com.gardenia.redis.spring.StartRedisApplication;
import com.gardenia.redis.spring.vo.Book;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * 测试: redis序列化
 * @author caimeng
 * @date 2025/4/2 17:42
 */
@SuppressWarnings("SpringBootApplicationProperties")
@Slf4j
@ExtendWith(SpringExtension.class)  // 使用Spring扩展
@ContextConfiguration(classes = StartRedisApplication.class)    // 上下文启动配置类
@TestPropertySource(properties = {
        "redis.serializer=string-java",
})
public class RedisObjectTest {
    @Autowired
    private RedisTemplate<String, Object> redisSerializerTemplate;
    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 测试: 根据条件加载 bean 对象
     */
    @Test
    public void redisTemplateLoadTest() {
        String[] beanNamesForType = applicationContext.getBeanNamesForType(RedisTemplate.class);
        for (String beanName : beanNamesForType) {
            /*
             * beanName: redisTemplateWithSerializer
             */
            log.info("beanName: {}", beanName);
        }
        Object redisTemplateWithSerializer = applicationContext.getBean("redisTemplateWithSerializer");
        assert redisTemplateWithSerializer == redisSerializerTemplate : "加载的是默认的bean";
    }

    /**
     * 测试: java对象存储
     */
    @Test
    public void saveObjTest() {
        Book book = new Book(101L, "Spring Boot实战", "李兴华", 99.9);
        String key = "serial:book";
        // 数据的存储
        redisSerializerTemplate.opsForValue().set(key, book);
        // 数据的加载
        Book readObj = (Book) redisSerializerTemplate.opsForValue().get(key);
        // 【普通对象数据】: Book(bid=101, name=Spring Boot实战, author=李兴华, price=99.9)
        log.info("【普通对象数据】: {}", readObj);
    }
}
