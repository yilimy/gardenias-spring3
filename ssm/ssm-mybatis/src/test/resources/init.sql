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