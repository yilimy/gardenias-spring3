CREATE TABLE project(
    pid BIGINT AUTO_INCREMENT comment '项目ID',
    name VARCHAR(50) comment '项目名称',
    charge VARCHAR(50) comment '项目主管',
    note TEXT comment '项目描述',
    status INT comment '项目状态',
    CONSTRAINT pk_pid PRIMARY KEY (pid)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8;