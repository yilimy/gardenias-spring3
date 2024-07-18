CREATE TABLE `emp2` (
                        `eid` varchar(50) NOT NULL COMMENT '雇员编号',
                        `ename` varchar(50) DEFAULT NULL COMMENT '雇员名称',
                        `job` varchar(50) DEFAULT NULL COMMENT '雇员职位',
                        `salary` double DEFAULT NULL COMMENT '基本工资',
                        PRIMARY KEY (`eid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;