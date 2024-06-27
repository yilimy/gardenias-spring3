package com.example.boot3.datajpa.dao;

import com.example.boot3.datajpa.StartSpringDataJPA;
import com.example.boot3.datajpa.po.Company;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

/**
 * @author caimeng
 * @date 2024/6/27 16:59
 */
@Slf4j
@ExtendWith(SpringExtension.class)
// 基于bean的启动
@ContextConfiguration(classes = StartSpringDataJPA.class)
public class ICompanyDaoTest {
    @Autowired
    private ICompanyDao iCompanyDao;
    @Test
    public void saveTest() {
        Company company = Company.builder()
                .name("欢愉神教无限公司")
                .capital(30000000.0)
                .num(999)
                .place("寰宇")
                .build();
        Company saved = iCompanyDao.save(company);
        /*
         * Hibernate: insert into Company (capital, name, num, place) values (?, ?, ?, ?)
         * 数据增加操作，增加后的数据ID：5
         */
        System.out.println("数据增加操作，增加后的数据ID：" + saved.getCid());
    }

    @Test
    public void findAllTest() {
        List<Company> companyList = iCompanyDao.findAll();
        /*
         * Company(cid=1, name=向上科技有限公司, capital=1000000.0, place=北京, num=50)
         * Company(cid=2, name=向下科技合伙公司, capital=500000.0, place=天津, num=70)
         * Company(cid=3, name=内卷科技无限公司, capital=500000.0, place=河北, num=100)
         * Company(cid=4, name=欢愉神教无限公司, capital=3.0E7, place=寰宇, num=999)
         * Company(cid=5, name=欢愉神教无限公司, capital=3.0E7, place=寰宇, num=999)
         */
        companyList.forEach(System.out::println);
    }
}
