package com.gardenia.database.action;

import com.gardenia.common.action.abs.AbstractBaseAction;
import com.gardenia.database.vo.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author caimeng
 * @date 2025/5/21 16:34
 */
@RestController
@RequestMapping("/member/*")
public class MemberAction extends AbstractBaseAction {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @RequestMapping("list")
    public Object echo() {
        //noinspection SqlResolve
        String sql = "select * from member3";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Member member = new Member();
            member.setMid(rs.getString("mid"));
            member.setName(rs.getString("name"));
            member.setAge(rs.getInt("age"));
            member.setSalary(rs.getDouble("salary"));
            member.setBirthday(rs.getDate("birthday"));
            member.setContent(rs.getString("content"));
            member.setDel(rs.getInt("del"));
            return member;
        });
    }
}
