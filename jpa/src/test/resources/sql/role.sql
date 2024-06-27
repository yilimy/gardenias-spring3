CREATE TABLE `role` (
                        `rid` varchar(50) NOT NULL COMMENT '角色编号',
                        `name` varchar(50) DEFAULT NULL COMMENT '角色名称',
                        PRIMARY KEY (`rid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;