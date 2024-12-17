package com.ssm.mybatis.mapper;

import com.ssm.mybatis.sql.MessageSQL;
import com.ssm.mybatis.vo.Message;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

/**
 * 使用SQL构建器实现的DAO
 * @see IMessageDao
 * @see IMessageAnnotationDao
 * @author caimeng
 * @date 2024/12/17 18:00
 */
@Mapper
public interface IMessageProviderDao {
    /**
     * 实现数据的增加
     * <p>
     *     实际上是将 {@link IMessageAnnotationDao#doCreate(Message)} 的 @Insert 注解换成了 @InsertProvider
     *     {@link MessageSQL#insertMessageSQL()}
     * @param message 消息实体对象
     * @return 影响条数
     */
    @InsertProvider(type = MessageSQL.class, method = "insertMessageSQL")
    @SelectKey(before = false,
            keyProperty = "mid", keyColumn = "mid",
            resultType = Long.class, statement = "SELECT LAST_INSERT_ID()")
    boolean doCreate(Message message);

    /**
     * 实现消息的分页查询
     * <p>
     *     实际上是将 {@link IMessageAnnotationDao#findAll(Map)} 的 @Select 注解换成了 @SelectProvider
     *     {@link MessageSQL#selectMessageSplitSQL()}
     * @param params 分页查询参数
     *               1. key=start, value=分页开始的数据行数
     *               2. key=line, value=每页抓取的数据量
     * @return 查询到的消息集合
     */
    @SelectProvider(type = MessageSQL.class, method = "selectMessageSplitSQL")
    List<Message> findAll(Map<String, Object> params);
}
