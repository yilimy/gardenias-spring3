package com.example.boot3.datajpa.dao;

import com.example.boot3.datajpa.StartSpringDataJPA;
import com.example.boot3.datajpa.po.Company;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author caimeng
 * @date 2024/7/1 11:59
 */
@Slf4j
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = StartSpringDataJPA.class)
public class ICompanyPagingBaseMapperTest {
    @Autowired
    private ICompanyPagingBaseMapper iCompanyPagingBaseMapper;

    @Test
    public void splitPageTest() {
        int currentPage = 1, pageSize = 2;
        // 根据“注册资金”的降序排列
        Sort sort = Sort.by(Sort.Direction.DESC, "capital");
        /*
         * 构建分页处理
         * page 从0开始 ：zero-based page index
         */
        PageRequest pageRequest = PageRequest.of(currentPage - 1, pageSize, sort);
        /*
         * Hibernate: select c1_0.cid,c1_0.capital,c1_0.name,c1_0.num,c1_0.place from Company c1_0 order by c1_0.capital desc limit ?,?
         * Hibernate: select count(c1_0.cid) from Company c1_0
         */
        Page<Company> pageInfo = iCompanyPagingBaseMapper.findAll(pageRequest);
        // 总记录数： 6, 总页数：3
        System.out.printf("总记录数： %s, 总页数：%s\n", pageInfo.getTotalElements(), pageInfo.getTotalPages());
        /*
         * Company(cid=5, name=欢愉神教无限公司, capital=3.0E7, place=寰宇, num=999)
         * Company(cid=4, name=欢愉神教无限公司, capital=3.0E7, place=寰宇, num=999)
         */
        pageInfo.getContent().forEach(System.out::println);
    }
}
