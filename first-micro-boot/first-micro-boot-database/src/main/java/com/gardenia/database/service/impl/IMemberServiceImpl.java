package com.gardenia.database.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gardenia.database.dao.IMemberDAO;
import com.gardenia.database.service.IMemberService;
import com.gardenia.database.vo.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @author caimeng
 * @date 2025/5/23 18:19
 */
@Service
public class IMemberServiceImpl implements IMemberService {
    @Autowired
    private IMemberDAO memberDAO;
    @Override
    public List<Member> fillAll() {
        return memberDAO.fillAll();
    }

    @Override
    public Member get(String mid) {
        return memberDAO.selectById(mid);
    }

    @Override
    public boolean add(Member member) {
        return memberDAO.insert(member) > 0;
    }

    @Override
    public boolean del(Set<String> mids) {
        return memberDAO.deleteBatchIds(mids) == mids.size();
    }

    @Override
    public IPage<Member> listSplit(int pageNum, int pageSize, String column, String keyword) {
        return memberDAO.selectPage(
                new Page<>(pageNum, pageSize),
                new QueryWrapper<Member>().like(column, keyword));
    }
}
