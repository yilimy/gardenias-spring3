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
import java.util.Set;

/**
 * @author caimeng
 * @date 2024/6/28 10:22
 */
@Slf4j
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = StartSpringDataJPA.class)
public class ICompanyMapperTest {
    @Autowired
    private ICompanyMapper iCompanyMapper;
    @Test
    public void findByCapitalGreaterThanTest() {
        List<Company> companyList = iCompanyMapper.findByCapitalGreaterThan(29999999);
        /*
         * Hibernate: select c1_0.cid,c1_0.capital,c1_0.name,c1_0.num,c1_0.place from Company c1_0 where c1_0.capital>?
         * Company(cid=4, name=欢愉神教无限公司, capital=3.0E7, place=寰宇, num=999)
         * Company(cid=5, name=欢愉神教无限公司, capital=3.0E7, place=寰宇, num=999)
         */
        companyList.forEach(System.out::println);
    }

    @Test
    public void findByCidInTest() {
        List<Company> companyList = iCompanyMapper.findByCidIn(Set.of(2L, 3L, 4L));
        /*
         * Hibernate: select c1_0.cid,c1_0.capital,c1_0.name,c1_0.num,c1_0.place from Company c1_0 where c1_0.cid in(?,?,?)
         * Company(cid=2, name=向下科技合伙公司, capital=500000.0, place=天津, num=70)
         * Company(cid=3, name=内卷科技无限公司, capital=500000.0, place=河北, num=100)
         * Company(cid=4, name=欢愉神教无限公司, capital=3.0E7, place=寰宇, num=999)
         */
        companyList.forEach(System.out::println);
    }

    @Test
    public void findByNameLikeOrderByCidDescTest() {
        List<Company> companyList = iCompanyMapper.findByNameLikeOrderByCidDesc("%无限公司");
        /*
         * Hibernate: select c1_0.cid,c1_0.capital,c1_0.name,c1_0.num,c1_0.place from Company c1_0 where c1_0.name like ? escape '\\' order by c1_0.cid desc
         * Company(cid=5, name=欢愉神教无限公司, capital=3.0E7, place=寰宇, num=999)
         * Company(cid=4, name=欢愉神教无限公司, capital=3.0E7, place=寰宇, num=999)
         * Company(cid=3, name=内卷科技无限公司, capital=500000.0, place=河北, num=100)
         */
        companyList.forEach(System.out::println);
    }

    @Test
    public void findByNameAndPlaceOrderByCidDescTest() {
        List<Company> companyList = iCompanyMapper.findByNameAndPlaceOrderByCidDesc("欢愉神教无限公司", "寰宇");
        /*
         * Hibernate: select c1_0.cid,c1_0.capital,c1_0.name,c1_0.num,c1_0.place from Company c1_0 where c1_0.name=? and c1_0.place=? order by c1_0.cid desc
         * Company(cid=5, name=欢愉神教无限公司, capital=3.0E7, place=寰宇, num=999)
         * Company(cid=4, name=欢愉神教无限公司, capital=3.0E7, place=寰宇, num=999)
         */
        companyList.forEach(System.out::println);
    }
}
