package com.gardenia.database.service.impl;

import com.gardenia.database.dao.IMemberDAO;
import com.gardenia.database.service.IMemberService;
import com.gardenia.database.vo.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
