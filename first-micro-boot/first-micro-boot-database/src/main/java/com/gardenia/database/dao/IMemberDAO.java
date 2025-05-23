package com.gardenia.database.dao;

import com.gardenia.database.vo.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author caimeng
 * @date 2025/5/23 18:10
 */
@Mapper
public interface IMemberDAO {
    List<Member> fillAll();
}
