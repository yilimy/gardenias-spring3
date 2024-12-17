package com.ssm.mybatis.service;

import com.ssm.mybatis.vo.Message;

import java.util.List;

/**
 * @author caimeng
 * @date 2024/12/17 11:48
 */
public interface IMessageService {
    boolean add(Message message);

    List<Message> list(int current, int line);
}
