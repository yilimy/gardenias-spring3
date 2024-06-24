CREATE TABLE `login` (
                         `uid` varchar(50) NOT NULL COMMENT '用户ID',
                         `password` varchar(32) DEFAULT NULL COMMENT '登录密码',
                         CONSTRAINT pk_uid1 PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;