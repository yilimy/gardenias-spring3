package com.ssm.mybatis.mapper;

import com.ssm.mybatis.vo.Message;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

import java.util.List;
import java.util.Map;

/**
 * 使用注解定义的SQL命令
 * <p>
 *     虽然看起来程序代码简单了，但是实际开发过程中，最复杂的是SQL命令的编写。
 *     将这些SQL定义为注解，其实不方便，尽量保留一个 Mapper.xml 映射文件。
 * @see IMessageDao
 * @author caimeng
 * @date 2024/12/17 16:24
 */
@Mapper
public interface IMessageAnnotationDao {

    /**
     * 实现数据的增加
     * @param message 消息实体对象
     * @return 影响条数
     */
    @Insert("INSERT INTO message(title, sender, content) VALUES (#{title}, #{sender}, #{content})")
    // 参考: mybatis/mapper/BookMapper.xml:59 doCreateAdapterOracle 中关于 SelectKey 标签的使用
    @SelectKey(before = false,
            keyProperty = "mid", keyColumn = "mid",
            resultType = Long.class, statement = "SELECT LAST_INSERT_ID()")
    boolean doCreate(Message message);

    /**
     * 实现消息的分页查询
     * @param params 分页查询参数
     *               1. key=start, value=分页开始的数据行数
     *               2. key=line, value=每页抓取的数据量
     * @return 查询到的消息集合
     */
    @Select("SELECT mid, title, sender, content FROM message LIMIT #{start}, #{line}")
    List<Message> findAll(Map<String, Object> params);
}
