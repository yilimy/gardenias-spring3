package com.example.boot3.valid.service;

import com.example.boot3.valid.pojo.UserValid;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author caimeng
 * @date 2024/5/17 17:26
 */
@ExtendWith(SpringExtension.class)
@SpringBootApplication
// 听说设置该webEnvironment后，SpringBoot启动会加速
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class UserValidServiceTest {
    @Autowired
    private UserValidService userValidService;

    /**
     * 测试：参数上的校验注解是否生效
     */
    @Test
    public void checkUserTest(){
        UserValid userValid = new UserValid();
        userValidService.checkUserNoEffect(userValid);
        // 因为传了空值，该处会报错
        userValidService.checkUser(userValid);
    }
}
