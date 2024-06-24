CREATE TABLE `dept` (
                        `deptno` bigint(20) NOT NULL COMMENT '部门编号',
                        `dname` varchar(50) DEFAULT NULL COMMENT '部门名称',
                        `loc` varchar(50) DEFAULT NULL COMMENT '部门位置',
                        PRIMARY KEY (`deptno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;