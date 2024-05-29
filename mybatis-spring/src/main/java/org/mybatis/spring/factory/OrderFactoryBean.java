package org.mybatis.spring.factory;

import com.example.mybatis.spring.mapper.OrderMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 自定义的mybatis工厂Bean类
 * @author caimeng
 * @date 2024/3/26 18:27
 */
//@Component
public class OrderFactoryBean implements FactoryBean<OrderMapper> {
    /**
     * 该类在 org.mybatis:mybatis:3.5.6+ 被移到其他包中了
     */
    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Override
    public OrderMapper getObject() throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        sqlSessionFactory.getConfiguration().addMapper(OrderMapper.class);
        return session.getMapper(OrderMapper.class);
    }

    @Override
    public Class<?> getObjectType() {
        return OrderMapper.class;
    }
}
