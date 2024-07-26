package com.example.boot3.spring.cache.service;

import com.example.boot3.spring.cache.po.Emp2;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;

/**
 * @author caimeng
 * @date 2024/7/18 17:43
 */
@SuppressWarnings("all")
// 在这里配置了缓存容器的名称，方法中就可以不用配置了
@CacheConfig(cacheNames = "emp")
public interface IEmp2Service {
    /**
     * 编辑雇员信息
     * <p>
     *     进行数据的更新操作，而在数据更新的时候会返回一个新的EMP对象
     *     如果要进行缓存的处理，那么主要依靠该对象进行数据的更新操作
     * <p>
     *     JSR-107标准
     * @param emp2 雇员信息，表示要更新的雇员数据
     * @return 编辑后的雇员信息，已经更新完成的数据
     */
    @CachePut(key = "#emp2.eid", unless = "#result == null")
    Emp2 edit(Emp2 emp2);

    /**
     * 多级缓存
     * 普通编辑类{@link this#edit(Emp2)}的方法多了额外的 CachePut, 使用 Caching讲这几个CachePut收集起来
     * @param emp2 雇员信息
     * @return 编辑后的雇员信息
     */
    @Caching(put = {
            // 根据雇员编号更新缓存
            @CachePut(key = "#emp2.eid", unless = "#result == null"),
            // 根据雇员名称更新缓存
            @CachePut(key = "#emp2.ename", unless = "#result == null")
    })
    Emp2 editCascade(Emp2 emp2);

    /**
     * 删除雇员信息
     * @param eid 雇员ID
     * @return 删除结果
     */
    @CacheEvict(key = "#eid")
    boolean delete(String eid);

    /**
     * 查询雇员信息
     * <p>
     *     警告的原因：Spring团队并不推荐在接口方法上使用这些缓存注解，
     *     原因在于Java的注解并不从接口继承，如果使用基于类的代理或者编织（aspectj）模式，
     *     缓存设置将不会被识别，导致对象不会被正确地包装在缓存代理中
     * <p>
     *     key=#eid 表示：使用当前查询的“eid”名称作为缓存的KEY，查询的ID一定就是返回的ID（前提是有返回数据）
     * @param eid 雇员ID
     * @return 雇员信息
     */
    @Cacheable(key = "#eid")
    Emp2 get(String eid);

    /**
     * 包含有 yootk 的key才会被缓存
     * <p>
     *     不包含的话，可以配置成 #eid.contains('yootk')
     * @param eid 雇员ID
     * @return 雇员信息
     */
    @Cacheable(key = "#eid", condition = "#eid.contains('yootk')")
    default Emp2 getWithCondition(String eid) {
        return get(eid);
    }

    /**
     * 薪资大于4000的返回数据，将不会被缓存
     * unless : 除非。满足条件的将会被排除
     * @param eid 雇员ID
     * @return 雇员信息
     */
    @Cacheable(key = "#eid", unless = "#result.salary > 4000")
    default Emp2 getWithSalary(String eid) {
        return get(eid);
    }

    /**
     * 同步缓存查询
     * @param eid 雇员ID
     * @return 雇员信息
     */
    @Cacheable(sync = true)
    default Emp2 getSync(String eid) {
        return get(eid);
    }

    /**
     * 混合缓存容器的查询
     * <p>
     *     类头中设置了 CacheConfig，并指定了缓存容器的名字为 emp
     *     方法中也设置了容器名称为 empMap
     *     以方法中设置的缓存容器名为最终结果(empMap)
     * @param eid 查询关键字
     * @return 查询结果
     */
    @Cacheable(cacheNames = "empMap")
    default Emp2 getByMix(String eid){
        return get(eid);
    }

    /**
     * 根据名称查询雇员信息
     * @param ename 雇员名称
     * @return 雇员信息
     */
    @Cacheable
    Emp2 getByEname(String ename);
}
