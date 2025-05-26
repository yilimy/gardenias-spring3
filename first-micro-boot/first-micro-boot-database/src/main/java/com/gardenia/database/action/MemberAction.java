package com.gardenia.database.action;

import com.gardenia.common.action.abs.AbstractBaseAction;
import com.gardenia.database.service.IMemberService;
import com.gardenia.database.vo.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * @author caimeng
 * @date 2025/5/21 16:34
 */
@RestController
@RequestMapping("/member/*")
public class MemberAction extends AbstractBaseAction {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private IMemberService memberService;

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

    @RequestMapping("add")
    public Object add(Member member) {
        //noinspection SqlResolve
        String sql = "insert into member3(mid, name, age, salary, birthday, content, del) values(?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql,
                member.getMid(),
                member.getName(),
                member.getAge(),
                member.getSalary(),
                member.getBirthday(),
                member.getContent(),
                member.getDel()
        );
    }

    @RequestMapping("del")
    public Object del() {
        // 执行一次危险操作，看看SQL防火墙功能是否触发
        //noinspection SqlResolve,SqlWithoutWhere
        String sql = "delete from member3";
        /*
         * java.sql.SQLException:
         *      sql injection violation, dbType mysql, druid-version 1.2.18, delete not allow : delete from member3
         *
         * 触发了监控：
         *      SQL防御统计 - 黑名单
         *      1  delete from member3  delete not allow  1
         */
        return jdbcTemplate.update(sql);
    }

    @RequestMapping("mp/get")
    public Object echoMP(String mid) {
        return memberService.get(mid);
    }

    @RequestMapping("mp/add")
    public Object addMP(Member member) {
        return memberService.add(member);
    }

    @RequestMapping("mp/del")
    public Object delMP(String... ids) {
        return memberService.del(Set.of(ids));
    }

    @RequestMapping("mp/split")
    public Object delMP(int pageNum, int pageSize, String column, String keyword) {
        return memberService.listSplit(pageNum, pageSize, column, keyword);
    }
}
