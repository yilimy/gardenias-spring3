package com.example.mybatis.spring.mapper;

import org.apache.ibatis.annotations.Select;

/**
 * @author caimeng
 * @date 2024/3/26 18:53
 */
public interface OrderMapper {
    @Select("select 'order-0031'")
    String getOrderId();
}
