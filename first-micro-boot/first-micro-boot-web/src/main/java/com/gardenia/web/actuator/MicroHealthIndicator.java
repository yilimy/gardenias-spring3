package com.gardenia.web.actuator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * 自定义 健康检查
 * @author caimeng
 * @date 2025/3/11 13:54
 */
@Component
public class MicroHealthIndicator implements HealthIndicator {
    @Override
    public Health health() {    // 返回健康状态
        int errorCode = new Random().nextInt(0, Integer.MAX_VALUE);
        System.out.println("当前健康检查状态：" + errorCode);
        if ((errorCode % 2) != 0) {   // 触发错误的条件
            /*
             * {
             *    "status": "DOWN",
             *    "components": {
             *      "diskSpace": {
             *        "status": "UP",
             *        "details": {
             *          "total": 214748360704,
             *          "free": 173721153536,
             *          "threshold": 10485760,
             *          "path": "D:\\Gardenias\\SpringBoot3Demo\\.",
             *          "exists": true
             *        }
             *      },
             *      "mail": {
             *        "status": "UP",
             *        "details": {
             *          "location": "smtp.qq.com:-1"
             *        }
             *      },
             *      "micro": {
             *        "status": "DOWN",
             *        "details": {
             *          "microServiceErrorCode": 2658257,
             *          "error": "java.lang.RuntimeException: 服务故障"
             *        }
             *      },
             *      "ping": {
             *        "status": "UP"
             *      }
             *    }
             *  }
             */
            return Health.down()
                    .withDetail("microServiceErrorCode", errorCode)
                    .withException(new RuntimeException("服务故障"))
                    .build();   // 当前状态：不健康
        }
        return Health.up().build(); // 当前状态为：健康
    }
}
