package com.example.boot3.valid.controller;

import com.example.boot3.valid.pojo.UserValid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author caimeng
 * @date 2024/5/17 17:56
 */
@Slf4j
@RestController
@RequestMapping("/valid")
public class UserValidController {

    @RequestMapping(value = "/checkUser", method = RequestMethod.POST)
    public String checkUser(@Validated @RequestBody UserValid user){
        log.info("controller 层的校验 : {}", user);
        return "通过了校验";
    }

}
