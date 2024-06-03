package com.example.boot3.jdbc.service;

import com.example.boot3.jdbc.pojo.Book;

/**
 * @author caimeng
 * @date 2024/6/3 15:04
 */
public interface PubWithoutAnnotationService {
    boolean add(Book book);
    boolean edit(Book book);
    boolean editAll();
}
