CREATE TABLE `book` (
                        `bid` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '图书ID',
                        `title` varchar(50) NOT NULL COMMENT '图书名称',
                        `author` varchar(50) NOT NULL COMMENT '图书作者',
                        `price` double DEFAULT NULL COMMENT '图书价格',
                        PRIMARY KEY (`bid`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8;

CREATE TABLE member2 (
    mid VARCHAR(50) comment '用户ID',
    name VARCHAR(50) comment '用户姓名',
    age INT comment '用户年龄',
    sex VARCHAR(10) comment '用户性别',
    score DOUBLE comment '学生成绩',
    major VARCHAR(50) comment '学生专业',
    salary DOUBLE comment '雇员收入',
    dept VARCHAR(50) comment '所属部门',
    type VARCHAR(50) comment '类型区分',
    CONSTRAINT pk_mid primary key(mid)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8;

CREATE TABLE account (
     aid VARCHAR(50) comment '账户ID',
     name VARCHAR(50) comment '账户姓名',
     status INT comment '账户锁定状态，0表示活跃，1表示锁定',
     CONSTRAINT pk_aid primary key (aid)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8;
insert into account (aid, name, status) values ('muyan', '沐言', 0);
insert into account (aid, name, status) values ('yootk', '优拓', 0);
insert into account (aid, name, status) values ('lixinghua', '李兴华', 1);