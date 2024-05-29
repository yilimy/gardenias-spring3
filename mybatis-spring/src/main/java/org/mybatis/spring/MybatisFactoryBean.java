package org.mybatis.spring;

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
public class MybatisFactoryBean implements FactoryBean {
    /**
     * 该类在 org.mybatis:mybatis:3.5.6+ 被移到其他包中了
     */
    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    private final Class<?> mClass;

    public MybatisFactoryBean(Class<?> mClass) {
        this.mClass = mClass;
    }

    @Override
    public Object getObject() {
        SqlSession session = sqlSessionFactory.openSession();
        // 通过 SqlSessionFactory 对 Mapper 进行代理
        sqlSessionFactory.getConfiguration().addMapper(mClass);
        // 完成代理后，通过 SqlSessionFactory 返回该代理的对象实例
        return session.getMapper(mClass);
    }

    @Override
    public Class<?> getObjectType() {
        // 先匹配类型，匹配到才会调用 getObject()
        return mClass;
    }
}
