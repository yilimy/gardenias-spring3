package com.gardenia.webflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * webFlux启动
 * <p>
 *     与SpringWeb的启动不同，webflux的启动是由Netty启动的
 *     Netty started on port 8080
 * @author caimeng
 * @date 2025/4/27 10:48
 */
@SpringBootApplication
public class StartWebFluxApplication {
    public static void main(String[] args) {
        SpringApplication.run(StartWebFluxApplication.class, args);
    }
}
