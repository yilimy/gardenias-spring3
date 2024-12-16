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

CREATE TABLE account_details(
    aid VARCHAR(50) comment '账户ID',
    rmb DOUBLE comment '人民币存款总额',
    dollar DOUBLE comment '美元存款总额',
    euro DOUBLE comment '欧元存款总额',
    CONSTRAINT pk_aid2 primary key (aid)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8;

CREATE TABLE role(
    rid VARCHAR(50) comment '角色ID',
    name VARCHAR(50) comment '角色名称',
    CONSTRAINT pk_rid primary key (rid)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8;
INSERT INTO `role` (`rid`, `name`) VALUES ('member', '用户管理');
INSERT INTO `role` (`rid`, `name`) VALUES ('system', '系统管理');

CREATE TABLE action(
    aid VARCHAR(50) comment '权限ID',
    name VARCHAR(50) comment '权限名称',
    rid VARCHAR(50) comment '角色ID',
    CONSTRAINT pk_aid primary key (aid)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8;
INSERT INTO `action` (`aid`, `name`, `rid`) VALUES ('member:lock', '用户锁定', 'member');
INSERT INTO `action` (`aid`, `name`, `rid`) VALUES ('member:verify', '用户验证', 'member');
INSERT INTO `action` (`aid`, `name`, `rid`) VALUES ('member:delete', '用户删除', 'member');
INSERT INTO `action` (`aid`, `name`, `rid`) VALUES ('system:init', '系统初始化', 'system');
INSERT INTO `action` (`aid`, `name`, `rid`) VALUES ('system:backup', '系统备份', 'system');