create table `member3`
(
    `mid`      varchar(50),
    `name`     varchar(10),
    `age`      int,
    `salary`   double,
    `birthday` date,
    `content`  text,
    `del`      int default 0,
    constraint pk_mid primary key (mid)
) engine = innodb
  default charset = utf8;

insert into member3
values ('m0001', '张三', 18, 5000, '2006-09-19', '["PHP", "Java", "C++"]', 0);
insert into member3
values ('m0002', '李四', 28, 6000, '1999-01-01', '["C++", "C#", "Python"]', 0);
insert into member3
values ('m0003', '王五', 38, 7000, '2004-08-13', '["C++", "C#", "Java"]', 0);

-- 多数据源表创建
CREATE TABLE `dept_multiple`
(
    `did` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '部门编号',
    `dname`  varchar(50) DEFAULT NULL COMMENT '部门名称',
    `loc`    varchar(50) DEFAULT NULL COMMENT '部门位置',
    `flag`    varchar(50) DEFAULT NULL COMMENT '数据库名称',
    CONSTRAINT pk_did PRIMARY KEY (`did`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

insert into dept_multiple(dname, loc, flag) values ('教学部', '北京', database());
insert into dept_multiple(dname, loc, flag) values ('财务部', '上海', database());
insert into dept_multiple(dname, loc, flag) values ('技术部', '洛阳', database());

CREATE TABLE `emp_multiple`
(
    `eid` varchar(50) COMMENT '雇员id',
    `ename`  varchar(50) DEFAULT NULL COMMENT '雇员姓名',
    `salary`    double COMMENT '薪资',
    `did`    bigint(20) COMMENT '所属部门',
    `flag`    varchar(50) DEFAULT NULL COMMENT '数据库名称',
    CONSTRAINT pk_eid PRIMARY KEY (`eid`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

insert into emp_multiple(eid, ename, salary, did, flag) values ('e0001', '张三', 5000, 1, database());
insert into emp_multiple(eid, ename, salary, did, flag) values ('e0002', '李四', 6000, 2, database());
insert into emp_multiple(eid, ename, salary, did, flag) values ('e0003', '王五', 7000, 3, database());
insert into emp_multiple(eid, ename, salary, did, flag) values ('e0004', '赵六', 8000, 1, database());
insert into emp_multiple(eid, ename, salary, did, flag) values ('e0005', '孙七', 9000, 2, database());