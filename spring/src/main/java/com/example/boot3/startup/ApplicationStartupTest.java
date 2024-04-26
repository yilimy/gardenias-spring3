package com.example.boot3.startup;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.core.metrics.ApplicationStartup;
import org.springframework.core.metrics.StartupStep;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * Startup 是spring的辅助记录功能，和Spring的核心功能没有太大关系
 * @author caimeng
 * @date 2024/4/25 10:48
 */
@Slf4j
public class ApplicationStartupTest {

    @SneakyThrows
    @Test
    public void DemoRun (){
        IApplicationStartup startup = new IApplicationStartup();
        // 创建启动的步骤
        StartupStep step = startup.start("application.start");
        for (int i = 0; i < 3; i++) {
            step.tag("start.step." + i, "服务启动逻辑 - " + i);
            TimeUnit.SECONDS.sleep(1);
        }
        // 结束当前的步骤
        step.end();
        log.info("Application启动标签");
        step.getTags().forEach(item -> log.info("【启动阶段】{} = {}", item.getKey(), item.getValue()));
    }

    static class IApplicationStartup implements ApplicationStartup {
        /**
         * 保存启动步骤的名称
         */
        private String name;
        /**
         * 启动步骤的触发时间
         */
        private Long timestamp;
        private final ITags tags = new ITags();
        @Override
        public StartupStep start(String name) {
            // 保存启动步骤的名称
            this.name = name;
            // 保存步骤开始的时间戳
            this.timestamp = System.currentTimeMillis();
            return new IStartupStep();
        }

        class IStartupStep implements StartupStep {
            @Override
            public String getName() {
                // 定义启动步骤的名称
                return IApplicationStartup.this.name;
            }

            @Override
            public long getId() {
                // 定义启动步骤的id，可以从分布式ID生成器中获取
                return 3;
            }

            @Override
            public Long getParentId() {
                return null;
            }

            @Override
            public StartupStep tag(String key, String value) {
                // 保存处理标签
                IApplicationStartup.this.tags.put(key, new ITag(key, value));
                return this;
            }

            @Override
            public StartupStep tag(String key, Supplier<String> value) {
                IApplicationStartup.this.tags.put(key, new ITag(key, value.get()));
                return this;
            }

            @Override
            public Tags getTags() {
                return IApplicationStartup.this.tags;
            }

            @Override
            public void end() {
                log.info("[end] -{}-步骤执行完毕，所耗费的时间为：{}",
                        IApplicationStartup.this.name, System.currentTimeMillis() - IApplicationStartup.this.timestamp);
            }
        }

        class ITags extends LinkedHashMap<String, StartupStep.Tag> implements StartupStep.Tags{

            @Override
            public Iterator<StartupStep.Tag> iterator() {
                return this.values().stream().iterator();
            }
        }

        class ITag implements StartupStep.Tag{
            private String key;
            private String value;

            public ITag(String key, String value) {
                this.key = key;
                this.value = value;
            }

            @Override
            public String getKey() {
                return this.key;
            }

            @Override
            public String getValue() {
                return this.value;
            }
        }
    }


}
