/**
 * Spring Data JPA
 * <p>
 *     简介  <a href="https://www.bilibili.com/video/BV1hz421b78W/" />
 *     之前讲解的JPA是为 SpringDataJPA 做铺垫的，结合了spring的JPA才能做到真正的简化
 *     SpringData不是JPA, Spring 为了便于数据操作技术的简化，提供了SpringData相关的技术，是一组技术的综合体。
 *     <a href="https://spring.io/projects/spring-data" />
 *     几乎所有能见到的数据库开发操作，SpringDataJPA都能胜任。
 *     如果按照之前讲解的代码开发形式，要融合到实际开发中的话，就会非常繁琐，
 *     因为在数据层的开发上需要不断引入JPA相关的接口实例进行各项数据的处理。
 *     最佳的做法是，由Spring帮助使用者生成一部分代码，开发者只需要定于DAO数据接口，而后就可以由SpringData技术自动生成接口的实现子类。
 * <p>
 *     传统的数据层代码开发步骤
 *     1. 由开发者根据自身的业务功能创建DAO接口，而后在接口中定义各类CRUD的处理；
 *     2. 需要由开发者自己开发DAO接口的子类，此时就只能够依靠EntityManager进行处理，很繁琐；
 *     3. 假设项目中存在有几十张数据表，接口的方法可能会存在有大量的重复定义。
 *     如果可以一次性解决DAO接口设计中存在的问题，并且可以让用户极大的简化数据层代码开发，那么一定可以提高项目的生产力。
 *     可以通过SpringDataJPA实现。
 * <p>
 *     SpringDataJPA 实现的核心关键在于要通过一个“@RepositoryDefinition”接口来提供最终的数据层标记的定义，
 *     而后这个标记的完成接口会由Spring容器启动的时候，自动维护一个bean实例，在业务开发的中直接引入接口的实例即可进行数据库的开发操作。
 *          - 缺陷：在复杂的查询上，写起来就比较痛苦了。
 * <p>
 *     本项目的开发中，将不再采用XML的配置文件的方式进行定义，毕竟要考虑到最终与SpringBoot的无缝对接。
 *     SpringBoot强调的是零配置环境，所以是不会编写任何的XML配置文件的。
 *     首先要解决扫描包的定义，在整个Spring里面通过扫描包的方式进行定义是非常常见的。
 * @author caimeng
 * @date 2024/6/27 10:43
 */
package com.example.boot3.datajpa.spring;