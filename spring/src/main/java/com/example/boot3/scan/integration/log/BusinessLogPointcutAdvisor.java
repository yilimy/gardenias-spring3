package com.example.boot3.scan.integration.log;

import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author caimeng
 * @date 2024/5/23 15:32
 */
public class BusinessLogPointcutAdvisor extends DefaultPointcutAdvisor implements InitializingBean {
    @Autowired
    private BusinessLogPointCut pointCut;
    @Autowired
    private BusinessLogAdvice advice;

    @Override
    public void afterPropertiesSet() throws Exception {
        setPointcut(pointCut);
        setAdvice(advice);
    }
}
