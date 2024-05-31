package com.example.boot3.junit;

import lombok.Data;

import java.util.UUID;

/**
 * @author caimeng
 * @date 2024/5/31 18:41
 */
@Data
public class NormalBean {
    private String id = UUID.randomUUID().toString();
}
