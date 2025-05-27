package com.gardenia.database.config;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import java.util.HashMap;
import java.util.Map;

/**
 * 自定义切面事务
 * @author caimeng
 * @date 2025/5/26 18:22
 */
@Configuration
public class TransactionConfig {
    // 事务处理的切面表达式
    public static final String AOP_POINTCUT_EXPRESSION = "execution(* com.gardenia.*.service.*.*(..))";
    // 事务处理的超时时间
    public static final int TX_TIMEOUT = 5;
    @Autowired
    public TransactionManager transactionManager;

    // 事务的拦截器
    @Bean("txAdvice")
    public TransactionInterceptor iTransactionInterceptor() {
        // 只读事务
        RuleBasedTransactionAttribute readOnlyAttribute = new RuleBasedTransactionAttribute();
        // 只读事务
        readOnlyAttribute.setReadOnly(true);
        // 非事务运行
        readOnlyAttribute.setPropagationBehavior(RuleBasedTransactionAttribute.PROPAGATION_NOT_SUPPORTED);
        // 一般事务
        RuleBasedTransactionAttribute requiredAttribute = new RuleBasedTransactionAttribute();
        // 开启事务
        requiredAttribute.setPropagationBehavior(RuleBasedTransactionAttribute.PROPAGATION_REQUIRED);
        // 事务处理的超时时间
        requiredAttribute.setTimeout(TX_TIMEOUT);
        Map<String, TransactionAttribute> transactionAttributeMap = new HashMap<>();
        // 新增数据的事务规则
        transactionAttributeMap.put("add*", requiredAttribute);
        // 修改数据的事务规则
        transactionAttributeMap.put("edit*", requiredAttribute);
        // 删除数据事务的规则
        transactionAttributeMap.put("delete*", requiredAttribute);
        // 查询数据事务的规则
        transactionAttributeMap.put("get*", readOnlyAttribute);
        // 定义方法名称的映射
        NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();
        source.setNameMap(transactionAttributeMap);
        // 配置事务拦截器
        TransactionInterceptor transactionInterceptor = new TransactionInterceptor();
        // 设置事务管理器
        transactionInterceptor.setTransactionManager(transactionManager);
        // 设置方法名称的映射
        transactionInterceptor.setTransactionAttributeSource(source);
        return transactionInterceptor;
    }

    @Bean
    public Advisor iMybatisAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(AOP_POINTCUT_EXPRESSION);
        // 设置切面表达式和拦截器
        return new DefaultPointcutAdvisor(pointcut, iTransactionInterceptor());
    }
}
