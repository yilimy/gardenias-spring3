package com.example.dubbo.consumer.test;

import com.alibaba.fastjson.JSON;
import com.example.dubbo.common.pojo.User;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

/**
 * @author caimeng
 * @date 2024/4/18 11:39
 */
@Slf4j
public class RpcTest {

    /**
     * 使用httpClient发送一个http请求
     * <a href="https://blog.csdn.net/whzhaochao/article/details/130512864"></a>
     */
    @SneakyThrows
    @Test
    public void httpClientTest() {
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8082/user/getUser"))
                .timeout(Duration.ofSeconds(10))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        User user = JSON.parseObject(response.body(), User.class);
        log.info("user = {}", user);
    }

}
