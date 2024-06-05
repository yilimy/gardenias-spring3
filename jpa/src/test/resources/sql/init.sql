CREATE TABLE `course` (
                          `cid` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '课程ID',
                          `cname` varchar(50) DEFAULT NULL COMMENT '课程名称',
                          `start` date DEFAULT NULL COMMENT '课程开始日期',
                          `end` date DEFAULT NULL COMMENT '课程结束日期',
                          `credit` int(11) DEFAULT NULL COMMENT '课程学分',
                          `num` int(11) DEFAULT NULL COMMENT '课程人数',
                          PRIMARY KEY (`cid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;