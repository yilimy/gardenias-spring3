package com.ssm.mybatis.mapper;

import com.ssm.mybatis.vo.Message;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 消息表的数据层接口
 * @author caimeng
 * @date 2024/12/17 11:33
 */
@Mapper     // mybatis自动生成的标记注解
public interface IMessageDao {
    /**
     * 实现数据的增加
     * @param message 消息实体对象
     * @return 影响条数
     */
    boolean doCreate(Message message);

    /**
     * 实现消息的分页查询
     * @param params 分页查询参数
     *               1. key=start, value=分页开始的数据行数
     *               2. key=line, value=每页抓取的数据量
     * @return 查询到的消息集合
     */
    List<Message> findAll(Map<String, Object> params);
}
