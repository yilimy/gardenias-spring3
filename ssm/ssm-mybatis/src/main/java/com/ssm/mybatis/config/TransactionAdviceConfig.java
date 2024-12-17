package com.ssm.mybatis.config;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 基于<i> @Aspect </i>注解的事务控制配置
 * 相较于xml: /spring/spring-transaction-aop.xml
 * @author caimeng
 * @date 2024/6/3 16:21
 */
@Configuration
@Aspect
public class TransactionAdviceConfig {

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        // PlatformTransactionManager 是一个事务控制标准，不同的数据库组件需要实现该标准
        return new DataSourceTransactionManager(dataSource);
    }

    /**
     * 定义事务控制
     * @param transactionManager 事务控制管理器
     * @return 事务控制拦截器
     */
    @Bean("txAdvice")
    public TransactionInterceptor transactionInterceptor(TransactionManager transactionManager) {
        // 只读配置
        RuleBasedTransactionAttribute readAttribute = new RuleBasedTransactionAttribute();
        // 设置只读
        readAttribute.setReadOnly(true);
        // 设置非事务运行
        readAttribute.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
        // 默认配置, PROPAGATION_REQUIRED
        RuleBasedTransactionAttribute requiredAttribute = new RuleBasedTransactionAttribute();
        // 针对不同的方法名，执行不同的事务策略
        Map<String, TransactionAttribute> nameMap = new HashMap<>();
        nameMap.put("add*", requiredAttribute);
        nameMap.put("edit*", requiredAttribute);
        nameMap.put("delete*", requiredAttribute);
        nameMap.put("get*", readAttribute);
        NameMatchTransactionAttributeSource tas = new NameMatchTransactionAttributeSource();
        tas.setNameMap(nameMap);
        return new TransactionInterceptor(transactionManager, tas);
    }

    /**
     * 定义切面
     * @param interceptor 拦截器
     * @return 切面
     */
    @Bean
    public Advisor transactionAdviceAdvisor(TransactionInterceptor interceptor) {
        String expression = "execution(public * com.ssm.mybatis..service.*(..))";
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(expression);
        return new DefaultPointcutAdvisor(pointcut, interceptor);
    }
}
