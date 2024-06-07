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
public class StaticBean {
    private String uid;

    static {
        System.out.println("StaticBean static ...");
    }

    public StaticBean() {
        this.uid = UUID.randomUUID().toString();
        System.out.println("StaticBean 非空构造");
    }

    public StaticBean(String uid) {
        this.uid = uid;
        System.out.println("StaticBean 有参构造");
    }

    public static void main(String[] args) {
        StaticBean bean = new StaticBean(UUID.randomUUID().toString());
        StaticBean2 bean2 = new StaticBean2();
        /*
         * StaticBean static ...
         * StaticBean 有参构造
         * StaticBean2 static ...
         * StaticBean2 非空构造
         * bean : StaticBean(uid=56e9cca6-4c26-4571-b8e7-0f4ac560200c)
         * bean2 : StaticBean2(uid=2282bd73-4b63-4806-9587-ae4b703e7399)
         */
        System.out.println("bean : " + bean);
        System.out.println("bean2 : " + bean2);
    }
}
