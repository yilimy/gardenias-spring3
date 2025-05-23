package com.gardenia.database.service;

import com.gardenia.database.StartDataBaseApplication;
import com.gardenia.database.vo.Member;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

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
}
