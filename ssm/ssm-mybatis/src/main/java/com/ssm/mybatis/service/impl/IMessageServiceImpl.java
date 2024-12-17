package com.ssm.mybatis.service.impl;

import com.ssm.mybatis.mapper.IMessageAnnotationDao;
import com.ssm.mybatis.mapper.IMessageDao;
import com.ssm.mybatis.mapper.IMessageProviderDao;
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
    @Autowired
    private IMessageAnnotationDao iMessageAnnotationDao;
    @Autowired
    private IMessageProviderDao iMessageProviderDao;
    @Override
    public boolean add(Message message) {
        return this.iMessageDao.doCreate(message);
    }

    @Override
    public boolean addWithAnnotation(Message message) {
        return this.iMessageAnnotationDao.doCreate(message);
    }

    @Override
    public boolean addWithProvider(Message message) {
        return this.iMessageProviderDao.doCreate(message);
    }


    @Override
    public List<Message> list(int current, int line) {
        return this.iMessageDao.findAll(Map.of("start", (current - 1) * line, "line", line));
    }

    @Override
    public List<Message> listWithAnnotation(int current, int line) {
        return this.iMessageAnnotationDao.findAll(Map.of("start", (current - 1) * line, "line", line));
    }

    @Override
    public List<Message> listWithProvider(int current, int line) {
        return this.iMessageProviderDao.findAll(Map.of("start", (current - 1) * line, "line", line));
    }
}
