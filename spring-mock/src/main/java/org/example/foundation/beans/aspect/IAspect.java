package org.example.foundation.beans.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * @author caimeng
 * @date 2024/6/17 17:27
 */
@Component
@Aspect
public class IAspect {

    @Before("execution(* org.example.foundation.beans.service.UserService.test())")
    public void before() {
        System.out.println("before ...");
    }
}
