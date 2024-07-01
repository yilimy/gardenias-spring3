package com.example.boot3.datajpa.dao;

import cn.hutool.core.util.RandomUtil;
import com.example.boot3.datajpa.StartSpringDataJPA;
import com.example.boot3.datajpa.po.Company;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author caimeng
 * @date 2024/7/1 13:56
 */
@Slf4j
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = StartSpringDataJPA.class)
public class ICompanyJpaMapperTest {
    @Autowired
    private ICompanyJpaMapper iCompanyJpaMapper;

    /**
     * 测试：批量添加
     */
    @Test
    public void addBatchTest() {
        List<Company> companyList = Stream.generate(() ->
                Company.builder()
                        .name("name_" + RandomUtil.randomString(8))
                        .capital(RandomUtil.randomDouble(300000, 2, RoundingMode.UP))
                        .num(RandomUtil.randomInt(1000))
                        .place("place_" + RandomUtil.randomString(8))
                        .build())
                .limit(100).toList();
        /*
         * 数据的批量存储
         * Hibernate: insert into Company (capital, name, num, place) values (?, ?, ?, ?)
         * ...
         */
        iCompanyJpaMapper.saveAllAndFlush(companyList);
    }

    @Test
    public void getId() {
        /*
         * 数据查询
         * 查看会不会发起查询语句
         * Hibernate: select c1_0.cid,c1_0.capital,c1_0.name,c1_0.num,c1_0.place from Company c1_0 where c1_0.cid=?
         * 会执行
         */
        Optional<Company> companyOptional = iCompanyJpaMapper.findById(5L);
    }

    /**
     * findById 在执行查询时会发出查询语句
     * {@link this#getId()}
     * getReferenceById 在执行查询时不会发出查询指令，当第一次使用数据时才发出查询语句
     */
    @Test
    public void referenceId() {
        /*
         * 数据查询
         * 查看会不会发起查询语句
         * 没有发出查询指令
         */
        Company company = iCompanyJpaMapper.getReferenceById(5L);
    }

    /**
     * 测试：从 getReferenceById 的返回结果中拿取数据
     * {@link this#referenceId()}
     */
    @Test
    public void referenceIdWithUse() {
        /*
         * 数据查询
         * 查看会不会发起查询语句
         * 报错：LazyInitializationException
         *      could not initialize proxy [com.example.boot3.datajpa.po.Company#5] - no Session
         *
         * 由于该操作的方法是在第一次调用getter方法的时候才会发出最终的查询操作，所以当业务处理完成后，并且将PO实例返回，
         * 就有可能出现Session已关闭的问题，从而无法查询。
         */
        Company company = iCompanyJpaMapper.getReferenceById(5L);
        System.out.println("公司注册地：" + company.getPlace());
    }
}
