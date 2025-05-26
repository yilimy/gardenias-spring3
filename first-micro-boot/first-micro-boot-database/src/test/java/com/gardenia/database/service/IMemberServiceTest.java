package com.gardenia.database.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gardenia.database.StartDataBaseApplication;
import com.gardenia.database.vo.Member;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * @author caimeng
 * @date 2025/5/23 18:28
 */
@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@SpringBootTest(classes = StartDataBaseApplication.class)
public class IMemberServiceTest {
    @Autowired
    private IMemberService memberService;

    @Test
    public void findAllTest() {
        List<Member> members = memberService.fillAll();
        System.out.println(members);
    }

    @Test
    public void getTest() {
        Member member = memberService.get("m0001");
        System.out.println(member);
    }

    @Test
    public void addTest() {
        Member member = new Member();
        member.setMid("m000" + new Random().nextInt(1000));
        member.setName("小王");
        member.setAge(new Random().nextInt(35));
        member.setSalary(Double.valueOf(String.format("%.2f", new Random().nextDouble(3000d, 50000d))));
        member.setBirthday(new Date());
        member.setContent("小王是一个好学生");
        boolean result = memberService.add(member);
        System.out.println(result);
    }

    @Test
    public void delTest() {
        boolean result = memberService.del(Set.of("m000816", "m000425"));
        System.out.println(result);
    }

    @Test
    public void listSplitTest() {
        IPage<Member> memberIPage = memberService.listSplit(1, 5, "mid", "m");
        System.out.println("总页数：" + memberIPage.getPages());
        System.out.println("总记录：" + memberIPage.getTotal());
        System.out.println("响应内容：" + memberIPage.getRecords());
    }
}
