package com.example.boot3.datajpa.dao;

import com.example.boot3.datajpa.StartSpringDataJPA;
import com.example.boot3.datajpa.po.Company;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author caimeng
 * @date 2024/6/28 14:05
 */
@Slf4j
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = StartSpringDataJPA.class)
public class ICompanyBaseMapperTest {
    @Autowired
    private ICompanyBaseMapper iCompanyBaseMapper;

    @Test
    public void saveTest() {
        Company company = Company.builder()
                .name("欢愉神教无限公司匹诺康尼事业部")
                .capital(60000020.1)
                .num(969)
                .place("寰宇")
                .build();
        Company saved = iCompanyBaseMapper.save(company);
        /*
         * Hibernate: insert into Company (capital, name, num, place) values (?, ?, ?, ?)
         * 数据增加操作，增加后的数据ID：9
         */
        System.out.println("数据增加操作，增加后的数据ID：" + saved.getCid());
    }

    @Test
    public void findAllTest() {
        iCompanyBaseMapper.findAll().forEach(System.out::println);
    }

    /**
     * 测试验证缓存
     */
    @SneakyThrows
    @Test
    public void getWithThreadTest() {
        Thread threadA = new Thread(() -> {
            Optional<Company> optional = iCompanyBaseMapper.findById(3L);
            optional.ifPresent(c -> System.out.println("线程[" + Thread.currentThread().getName() + "], Company = " + c));
        }, "查询线程A");
        Thread threadB = new Thread(() -> {
            Optional<Company> optional = iCompanyBaseMapper.findById(3L);
            optional.ifPresent(c -> System.out.println("线程[" + Thread.currentThread().getName() + "], Company = " + c));
        }, "查询线程B");
        threadA.start();
        // 需要给二级缓存一定的处理时间，如果不设置延迟时间，缓存可能不会被触发
        TimeUnit.SECONDS.sleep(1);
        threadB.start();
        /*
         * Hibernate: select c1_0.cid,c1_0.capital,c1_0.name,c1_0.num,c1_0.place from Company c1_0 where c1_0.cid=?
         * 线程[查询线程A], Company = Company(cid=3, name=内卷科技无限公司, capital=500000.0, place=河北, num=100)
         * 线程[查询线程B], Company = Company(cid=3, name=内卷科技无限公司, capital=500000.0, place=河北, num=100)
         */
        TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
    }

    @Test
    public void deleteTest() {
        // Hibernate: delete from Company where cid=?
        iCompanyBaseMapper.deleteById(9L);
    }
}
