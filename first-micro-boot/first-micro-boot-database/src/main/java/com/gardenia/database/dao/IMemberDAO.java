package com.gardenia.database.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gardenia.database.vo.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author caimeng
 * @date 2025/5/23 18:10
 */
@Mapper
public interface IMemberDAO extends BaseMapper<Member> {
    List<Member> fillAll();
}
