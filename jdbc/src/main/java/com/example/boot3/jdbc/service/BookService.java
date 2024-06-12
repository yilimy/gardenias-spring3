package com.example.boot3.jdbc.service;

import com.example.boot3.jdbc.pojo.Book;

/**
 * @author caimeng
 * @date 2024/6/3 11:50
 */

public interface BookService {

    void remove();

    void removeSuccess();
    boolean add(Book book);
    boolean edit(Book book);

    /**
     * 测试JDK的事务控制注解
     */
    void proxyTransactional();

}
