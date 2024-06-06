package com.example.mybatis.core.mapper;

import com.example.mybatis.core.pojo.Blog;
import org.apache.ibatis.annotations.Select;

/**
 * @author caimeng
 * @date 2024/5/29 14:44
 */
public interface BlogMapper {
    Blog selectBlog(int id);
    @Select("select * from blog where id=#{id}")
    Blog selectBlogByAnnotation(int id);
}
