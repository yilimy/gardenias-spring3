package com.ssm.mybatis.service.impl;

import com.ssm.mybatis.mapper.IMessageDao;
import com.ssm.mybatis.service.IMessageService;
import com.ssm.mybatis.vo.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author caimeng
 * @date 2024/12/17 11:52
 */
@Service
public class IMessageServiceImpl implements IMessageService {
    @Autowired
    private IMessageDao iMessageDao;
    @Override
    public boolean add(Message message) {
        return this.iMessageDao.doCreate(message);
    }

    @Override
    public List<Message> list(int current, int line) {
        return this.iMessageDao.findAll(Map.of("start", (current - 1) * line, "line", line));
    }
}
