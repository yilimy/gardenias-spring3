CREATE TABLE `company` (
                           `cid` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '公司ID',
                           `name` varchar(50) DEFAULT NULL COMMENT '公司名称',
                           `capital` double DEFAULT NULL COMMENT '注册资金',
                           `place` varchar(50) DEFAULT NULL COMMENT '注册地址',
                           `num` int(11) DEFAULT NULL COMMENT '员工数量',
                           PRIMARY KEY (`cid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;