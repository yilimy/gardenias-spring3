package com.example.boot3.datajpa.dao;

import com.example.boot3.datajpa.po.Company;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * 通过实现父接口的方式来定义数据服务
 * 相比于 {@link ICompanyDao} 的注解方式
 * <p>
 *     简单的增加和查询肯定不够正常的工作需要，需要对接口进行扩充。
 *     1. 直接写上具体的语句
 *     2. 通过方法名称配置，根据方法名称来决定功能的使用
 * @author caimeng
 * @date 2024/6/27 17:26
 */
public interface ICompanyInterfaceDao extends Repository<Company, Long> {
    /**
     * 实现数据增加
     * @param company 数据实体
     * @return 增加后的对象数据
     */
    Company save(Company company);

    /**
     * 查询所有
     * @return 数据实例列表
     */
    List<Company> findAll();

    // --------------- 扩展 ---------------

    /**
     * 更新基础信息
     * @param company 待更新的数据对象
     * @return 影响条数
     */
    // 使用jpql语句设置参数
    // 因为要获取更新后的结果（影响条数），所以使用的是 @Query
    @Query("UPDATE Company AS c SET c.capital=:#{#param.capital}, c.num=:#{#param.num} " +
            "WHERE c.cid=:#{#param.cid}")
    // 因为本次测试没有Service层，因此把事务注解放到了Dao的接口上
    @Transactional
    // 更新数据缓存
    @Modifying(clearAutomatically = true)
    // 注解 @Param 定义的 jpql 使用的参数名，通过 :#{#param.属性} 进行对应属性的引用
    int edit(@Param("param") Company company);

    /**
     * 根据ID删除数据
     * @param id 数据ID
     * @return 影响条数
     */
    @Query("DELETE FROM Company AS c WHERE c.cid=:pid")
    @Transactional
    @Modifying(clearAutomatically = true)
    int removeById(@Param("pid") Long id);

    /**
     * 批量删除
     * @param ids 待删除的数据ID
     * @return 影响条数
     */
    @Query("DELETE FROM Company AS c WHERE c.cid IN :pids")
    @Transactional
    @Modifying(clearAutomatically = true)
    int removeBatch(@Param("pids") Set<Long> ids);

    /**
     * 查询所有
     * @return 数据集合
     */
    @Query("SELECT c FROM Company AS c")
    List<Company> findAllByJpql();

    /**
     * 根据ID查询数据
     * <p>
     *     Ideal插件提示 ?#{[0]} 这种写法有错误，但是能够正常执行
     * @param id 数据ID
     * @return 数据对象
     */
    @Query("SELECT c FROM Company AS c WHERE c.cid=?#{[0]}")
    Company findById(Long id);

    /**
     * 根据ID查询数据
     * <p>
     *     占位符的另一种写法，起始从 1 开始
     * @param id 数据ID
     * @return 数据对象
     */
    @Query("SELECT c FROM Company AS c WHERE c.cid=?1")
    Company findByIdPosition(Long id);

    /**
     * 根据ID查询数据
     * @param ids 数据ID
     * @return 数据对象
     */
    @Query("SELECT c FROM Company AS c WHERE c.cid IN :pids")
    List<Company> findByIds(@Param("pids") Set<Long> ids);

    /**
     * 根据ID和名字查询数据
     * @param company 查询参数
     * @return 查询结果
     */
    @Query("SELECT c FROM Company AS c WHERE c.cid=:#{#param.cid} AND c.name=:#{#param.name}")
    Company findByIdAndName(@Param("param") Company company);
}
