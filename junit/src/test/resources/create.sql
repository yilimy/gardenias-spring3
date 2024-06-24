CREATE TABLE `junit_user` (
                              `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
                              `username` varchar(100) NOT NULL COMMENT '用户名称',
                              `phone` varchar(50) NOT NULL COMMENT '电话',
                              PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

CREATE TABLE `junit_user_feature` (
                                      `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
                                      `user_id` bigint(20) NOT NULL COMMENT '用户表的主键',
                                      `feature_value` varchar(150) NOT NULL COMMENT '用户特征值',
                                      PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户特征表';