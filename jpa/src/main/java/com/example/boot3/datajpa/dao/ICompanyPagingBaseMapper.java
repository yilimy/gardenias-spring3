package com.example.boot3.datajpa.dao;

import com.example.boot3.datajpa.po.Company;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * 带分页的 自动实现CRUD的数据层处理服务接口
 * <p>
 *     在进行数据分页的时候，所有要分页的数据信息都要定义在 {@link Pageable} 对象实例中
 *     Pageable 也是一个接口，如果要想使用其进行数据的查询操作，可以通过 {@link PageRequest} 类提供的方法获取实例。
 *
 * @author caimeng
 * @date 2024/7/1 11:39
 */
public interface ICompanyPagingBaseMapper extends PagingAndSortingRepository<Company, Long>, CrudRepository<Company, Long> {
}
