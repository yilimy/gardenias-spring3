package com.example.mybatis.core.mapper;

import com.example.mybatis.core.pojo.Blog;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author caimeng
 * @date 2024/5/29 14:44
 */
public interface BlogMapper {
    Blog selectBlog(int id);
    @Select("select * from blog where id=#{id}")
    Blog selectBlogByAnnotation(int id);

    /**
     * 通过ID查询，但是以 resultMap 返回对象进行映射
     * 丢失“时分秒”精度的查询
     * @param id 待查询ID
     * @return 返回对象
     */
    Blog selectToDateMapById(@Param("id") int id);

    /**
     * 通过ID查询，但是以 resultMap 返回对象进行映射
     * @param id 待查询ID
     * @return 返回对象
     */
    Blog selectByTimeStampMapById(@Param("id") int id);
    /**
     * 新增一条数据，日期类型为 Date
     * @param blog 新增数据对象
     * @return 新增结果
     */
    int addByDate(Blog blog);
    /**
     * 新增一条数据，日期类型为 TIMESTAMP
     * @param blog 新增数据对象
     * @return 新增结果
     */
    int addByTimeStamp(Blog blog);
}
