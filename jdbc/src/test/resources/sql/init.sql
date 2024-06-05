CREATE TABLE `book` (
                        `bid` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '图书ID',
                        `title` varchar(50) NOT NULL COMMENT '图书名称',
                        `author` varchar(50) NOT NULL COMMENT '图书作者',
                        `price` double DEFAULT NULL COMMENT '图书价格',
                        PRIMARY KEY (`bid`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8;