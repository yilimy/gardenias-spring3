CREATE TABLE `member` (
                          `mid` varchar(50) NOT NULL COMMENT '用户编号',
                          `password` varchar(50) DEFAULT NULL COMMENT '登录密码',
                          PRIMARY KEY (`mid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;