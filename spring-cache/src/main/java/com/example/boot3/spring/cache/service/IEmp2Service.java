package com.example.boot3.spring.cache.service;

import com.example.boot3.spring.cache.po.Emp2;
import org.springframework.cache.annotation.Cacheable;

/**
 * @author caimeng
 * @date 2024/7/18 17:43
 */
@SuppressWarnings("all")
public interface IEmp2Service {
    /**
     * 编辑雇员信息
     * @param emp2 雇员信息
     * @return 编辑后的雇员信息
     */
    Emp2 edit(Emp2 emp2);

    /**
     * 删除雇员信息
     * @param eid 雇员ID
     * @return 删除结果
     */
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
    @Cacheable(cacheNames = "emp")
    Emp2 get(String eid);

    /**
     * 包含有 yootk 的key才会被缓存
     * <p>
     *     不包含的话，可以配置成 #eid.contains('yootk')
     * @param eid 雇员ID
     * @return 雇员信息
     */
    @Cacheable(cacheNames = "emp", key = "#eid", condition = "#eid.contains('yootk')")
    default Emp2 getWithCondition(String eid) {
        return get(eid);
    }

    /**
     * 薪资大于4000的返回数据，将不会被缓存
     * unless : 除非。满足条件的将会被排除
     * @param eid 雇员ID
     * @return 雇员信息
     */
    @Cacheable(cacheNames = "emp", key = "#eid", unless = "#result.salary > 4000")
    default Emp2 getWithSalary(String eid) {
        return get(eid);
    }

    /**
     * 同步缓存查询
     * @param eid 雇员ID
     * @return 雇员信息
     */
    @Cacheable(cacheNames = "emp", sync = true)
    default Emp2 getSync(String eid) {
        return get(eid);
    }

    /**
     * 根据名称查询雇员信息
     * @param ename 雇员名称
     * @return 雇员信息
     */
    @Cacheable(cacheNames = "emp")
    Emp2 getByEname(String ename);
}
