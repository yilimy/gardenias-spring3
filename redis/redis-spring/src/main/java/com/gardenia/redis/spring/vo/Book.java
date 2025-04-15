package com.gardenia.redis.spring.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author caimeng
 * @date 2025/4/2 17:39
 */
@Data
@NoArgsConstructor
public class Book implements Serializable {     // Serializable 是java序列化的核心
    private Long bid;
    private String name;
    private String author;
    private Double price;

    public Book(Long bid, String name, String author, Double price) {
        this.bid = bid;
        this.name = name;
        this.author = author;
        this.price = price;
    }
}
