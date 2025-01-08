package com.ssm.task.task;

import com.ssm.task.StartSpringTaskApplication;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.concurrent.TimeUnit;

/**
 * @author caimeng
 * @date 2025/1/8 16:36
 */
@ContextConfiguration(classes = StartSpringTaskApplication.class)
@ExtendWith(SpringExtension.class)
public class YootkTaskTest {

    /**
     * 测试：定时任务
     */
    @SneakyThrows
    @Test
    public void runTest(){
        // 启动容器，定时任务会在后台执行
        TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
    }
}
