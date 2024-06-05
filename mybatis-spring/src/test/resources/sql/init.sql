CREATE TABLE `blog` (
                        `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
                        `user_name` varchar(255) DEFAULT NULL COMMENT '用户',
                        `title` varchar(255) DEFAULT NULL COMMENT '标题',
                        `context` varchar(255) DEFAULT NULL COMMENT '内容',
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=102 DEFAULT CHARSET=utf8;