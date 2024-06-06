package com.example.mybatis.core.mapper;

import com.example.mybatis.core.pojo.Blog;
import org.apache.ibatis.annotations.Select;

/**
 * @author caimeng
 * @date 2024/6/5 13:59
 */
public interface BlogWithAnnotationMapper {
    @Select("select * from blog where id=#{id}")
    Blog selectBlogByAnnotation(int id);

    /**
     * 可拼接的value值
     * Select 支持多个value的字符串拼接
     * @see org.apache.ibatis.builder.annotation.MapperAnnotationBuilder#buildSqlSourceFromStrings(String[], Class, org.apache.ibatis.scripting.LanguageDriver)
     * @param id 自增ID
     * @return 查询结果
     */
    @Select(value = {"select * ", "from blog where id=#{id}"})
    Blog selectBlogByValues(int id);
}
