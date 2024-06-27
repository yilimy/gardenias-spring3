package com.example.boot3.jpa.relevancy;

import com.example.boot3.jpa.EntityManagerBaseTest;
import com.example.boot3.jpa.po.Member;
import com.example.boot3.jpa.po.Role;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author caimeng
 * @date 2024/6/26 18:59
 */
public class ManyToManyTest extends EntityManagerBaseTest {
    /**
     * 测试：多对多新增
     * <p>
     *     Hibernate: insert into Member (password, mid) values (?, ?)
     *     Hibernate: insert into member_role (mid, rid) values (?, ?)
     *     Hibernate: insert into member_role (mid, rid) values (?, ?)
     * 此时，在进行数据保存的时候，会自动依据关联结构的配置，向member_role关联表里添加所需要的数据，
     * 不会修改role表中的数据项，仅仅是进行了一个id的关联配置
     */
    @Test
    public void addTest() {
        Member member = Member.builder()
                .mid("ABC")
                .password("456789")
                .roles(new ArrayList<>())
                .build();
        List<String> roleList = Arrays.asList("dept", "emp");
        List<Role> roles = member.getRoles();
        roleList.stream().map(Role::new).forEach(roles::add);
        entityManager.getTransaction().begin();
        entityManager.persist(member);
        entityManager.getTransaction().commit();
    }

    @Test
    public void findTest() {
        /*
         * Hibernate: select m1_0.mid,m1_0.password from Member m1_0 where m1_0.mid=?
         * 默认只查询了member表中的数据，没有查询关联表中的信息
         */
        Member member = entityManager.find(Member.class, "baidu");
        /*
         * Hibernate: select m1_0.mid,m1_0.password from Member m1_0 where m1_0.mid=?
         * 第一次查询，member : Member(mid=baidu, password=123456)
         */
        System.out.println("第一次查询，member : " + member);
        /*
         * Hibernate: select r1_0.mid,r1_1.rid,r1_1.name from member_role r1_0 join Role r1_1 on r1_1.rid=r1_0.rid where r1_0.mid=?
         * 第二次查询，roles : [Role(rid=dept, name=部门管理), Role(rid=emp, name=雇员管理)]
         *
         * 由于多对多数据本身也存在有级联操作的问题，所以默认使用的就是延迟加载的处理
         * 即：当使用到授权信息的时候，才会通过指定的数据表进行加载处理。
         */
        System.out.println("第二次查询，roles : " + member.getRoles());
    }
}
