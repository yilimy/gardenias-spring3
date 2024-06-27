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
 * @date 2024/6/27 18:21
 */
@Slf4j
@ExtendWith(SpringExtension.class)
// 基于bean的启动
@ContextConfiguration(classes = StartSpringDataJPA.class)
public class ICompanyInterfaceDaoTest {
    @Autowired
    private ICompanyInterfaceDao iCompanyInterfaceDao;
    @Test
    public void saveTest() {
        Company company = Company.builder()
                .name("欢愉神教无限公司分公司")
                .capital(30000001.0)
                .num(999)
                .place("寰宇")
                .build();
        Company saved = iCompanyInterfaceDao.save(company);
        /*
         * Hibernate: insert into Company (capital, name, num, place) values (?, ?, ?, ?)
         * 数据增加操作，增加后的数据ID：6
         */
        System.out.println("数据增加操作，增加后的数据ID：" + saved.getCid());
    }

    /**
     * 测试：查询
     * {@link org.springframework.data.repository.core.support.RepositoryFactorySupport}
     * <p>
     *     18:24:00.742 [main] DEBUG org.springframework.data.repository.config.RepositoryConfigurationDelegate - Scanning for JPA repositories in packages com.example.boot3.datajpa.dao.
     *     18:24:00.757 [main] DEBUG org.springframework.data.repository.config.RepositoryComponentProvider - Identified candidate component class: file [D:\Gardenias\SpringBoot3Demo\jpa\target\classes\com\example\boot3\datajpa\dao\ICompanyDao.class]
     *     18:24:00.757 [main] DEBUG org.springframework.data.repository.config.RepositoryComponentProvider - Identified candidate component class: file [D:\Gardenias\SpringBoot3Demo\jpa\target\classes\com\example\boot3\datajpa\dao\ICompanyInterfaceDao.class]
     *     ...
     *     18:24:03.601 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'jpa.ICompanyInterfaceDao.fragments#0'
     *     18:24:03.601 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'ICompanyInterfaceDao'
     *     ...
     *     18:24:03.775 [main] DEBUG org.springframework.data.repository.core.support.RepositoryFactorySupport - Initializing repository instance for com.example.boot3.datajpa.dao.ICompanyInterfaceDao…
     *     18:24:03.830 [main] DEBUG org.springframework.data.repository.core.support.RepositoryFactorySupport - Finished creation of repository instance for com.example.boot3.datajpa.dao.ICompanyInterfaceDao.
     */
    @Test
    public void findAllTest() {
        List<Company> companyList = iCompanyInterfaceDao.findAll();
        /*
         * Company(cid=1, name=向上科技有限公司, capital=1000000.0, place=北京, num=50)
         * Company(cid=2, name=向下科技合伙公司, capital=500000.0, place=天津, num=70)
         * Company(cid=3, name=内卷科技无限公司, capital=500000.0, place=河北, num=100)
         * Company(cid=4, name=欢愉神教无限公司, capital=3.0E7, place=寰宇, num=999)
         * Company(cid=5, name=欢愉神教无限公司, capital=3.0E7, place=寰宇, num=999)
         * Company(cid=6, name=欢愉神教无限公司分公司, capital=3.0000001E7, place=寰宇, num=999)
         */
        companyList.forEach(System.out::println);
    }

    @Test
    public void editTest() {
        Company company = Company.builder().cid(6L).capital(29999999D).num(998).build();
        int edit = iCompanyInterfaceDao.edit(company);
        // edit result = 1
        System.out.println("edit result = " + edit);
    }

    @Test
    public void removeByIdTest() {
        int i = iCompanyInterfaceDao.removeById(7L);
        /*
         * 已存在 : remove result = 1
         * 不存在 : remove result = 0
         */
        System.out.println("remove result = " + i);
    }

    @Test
    public void removeBatchTest() {
        int i = iCompanyInterfaceDao.removeBatch(Set.of(7L, 8L));
        /*
         * Hibernate: delete from Company where cid in(?,?)
         * remove result = 2
         */
        System.out.println("remove result = " + i);
    }

    @Test
    public void findAllByJpqlTest() {
        List<Company> allByJpql = iCompanyInterfaceDao.findAllByJpql();
        /*
         * Hibernate: select c1_0.cid,c1_0.capital,c1_0.name,c1_0.num,c1_0.place from Company c1_0
         * Company(cid=1, name=向上科技有限公司, capital=1000000.0, place=北京, num=50)
         * Company(cid=2, name=向下科技合伙公司, capital=500000.0, place=天津, num=70)
         * Company(cid=3, name=内卷科技无限公司, capital=500000.0, place=河北, num=100)
         * Company(cid=4, name=欢愉神教无限公司, capital=3.0E7, place=寰宇, num=999)
         * Company(cid=5, name=欢愉神教无限公司, capital=3.0E7, place=寰宇, num=999)
         * Company(cid=6, name=欢愉神教无限公司分公司, capital=2.9999999E7, place=寰宇, num=998)
         */
        allByJpql.forEach(System.out::println);
    }

    @Test
    public void findByIdTest() {
        Company company = iCompanyInterfaceDao.findById(6L);
        /*
         * Hibernate: select c1_0.cid,c1_0.capital,c1_0.name,c1_0.num,c1_0.place from Company c1_0 where c1_0.cid=?
         * findById result = Company(cid=6, name=欢愉神教无限公司分公司, capital=2.9999999E7, place=寰宇, num=998)
         */
        System.out.println("findById result = " + company);
    }

    @Test
    public void findByIdPositionTest() {
        Company company = iCompanyInterfaceDao.findByIdPosition(5L);
        /*
         * Hibernate: select c1_0.cid,c1_0.capital,c1_0.name,c1_0.num,c1_0.place from Company c1_0 where c1_0.cid=?
         * findByIdPosition result = Company(cid=5, name=欢愉神教无限公司, capital=3.0E7, place=寰宇, num=999)
         */
        System.out.println("findByIdPosition result = " + company);
    }

    @Test
    public void findByIdsTest() {
        List<Company> companyList = iCompanyInterfaceDao.findByIds(Set.of(5L, 6L));
        /*
         * Hibernate: select c1_0.cid,c1_0.capital,c1_0.name,c1_0.num,c1_0.place from Company c1_0 where c1_0.cid in(?,?)
         * Company(cid=5, name=欢愉神教无限公司, capital=3.0E7, place=寰宇, num=999)
         * Company(cid=6, name=欢愉神教无限公司分公司, capital=2.9999999E7, place=寰宇, num=998)
         */
        companyList.forEach(System.out::println);
    }

    @Test
    public void findByIdAndNameTest() {
        Company company = Company.builder().cid(4L).name("欢愉神教无限公司").build();
        Company result = iCompanyInterfaceDao.findByIdAndName(company);
        /*
         * Hibernate: select c1_0.cid,c1_0.capital,c1_0.name,c1_0.num,c1_0.place from Company c1_0 where c1_0.cid=? and c1_0.name=?
         * result = Company(cid=4, name=欢愉神教无限公司, capital=3.0E7, place=寰宇, num=999)
         */
        System.out.println("result = " + result);
    }
}
