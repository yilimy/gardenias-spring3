CREATE TABLE `emp` (
                       `empno` bigint(20) NOT NULL COMMENT '雇员编号',
                       `ename` varchar(50) DEFAULT NULL COMMENT '雇员名称',
                       `sal` double DEFAULT NULL COMMENT '基本工资',
                       `deptno` bigint(20) DEFAULT NULL COMMENT '部门编号',
                       PRIMARY KEY (`empno`),
                       KEY `fk_deptno` (`deptno`),
                       CONSTRAINT `fk_deptno` FOREIGN KEY (`deptno`) REFERENCES `dept` (`deptno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;