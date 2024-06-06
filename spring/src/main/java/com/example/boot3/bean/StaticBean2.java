package com.example.boot3.bean;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

/**
 * @author caimeng
 * @date 2024/6/6 17:04
 */
@Data
@Builder
public class StaticBean2 {
    private String uid;

    static {
        System.out.println("StaticBean2 static ...");
    }

    public StaticBean2() {
        this.uid = UUID.randomUUID().toString();
        System.out.println("StaticBean2 非空构造");
    }

    public StaticBean2(String uid) {
        this.uid = uid;
        System.out.println("StaticBean2 有参构造");
    }
}
