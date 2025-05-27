package com.gardenia.database.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gardenia.database.vo.Member;

import java.util.List;
import java.util.Set;

/**
 * @author caimeng
 * @date 2025/5/23 18:18
 */
public interface IMemberService {
    List<Member> fillAll();

    /**
     * 根据Id查询
     * @param mid 主键
     * @return member
     */
    Member get(String mid);
    boolean add(Member member);
    boolean del(Set<String> mids);
    IPage<Member> listSplit(int pageNum, int pageSize, String column, String keyword);

    /**
     * 测试切面事务
     * {@link com.gardenia.database.config.TransactionConfig}
     * @param mids 参数
     * @return 结果
     */
    boolean addBatch(String... mids);
}
