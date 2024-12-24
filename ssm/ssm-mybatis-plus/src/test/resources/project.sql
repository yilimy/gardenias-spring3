CREATE TABLE project(
    pid BIGINT AUTO_INCREMENT comment '项目ID',
    name VARCHAR(50) comment '项目名称',
    charge VARCHAR(50) comment '项目主管',
    note TEXT comment '项目描述',
    status INT comment '项目状态',
    CONSTRAINT pk_pid PRIMARY KEY (pid)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8;
-- 新增乐观锁
ALTER TABLE project ADD version INT default 1;
-- 新增租户功能
ALTER TABLE project ADD tenant_id VARCHAR(50) default 'muyan';