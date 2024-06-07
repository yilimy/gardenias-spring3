CREATE TABLE dept(
    deptno BIGINT AUTO_INCREMENT COMMENT '部门编号',
    dname VARCHAR(50) COMMENT '部门名称',
    CONSTRAINT pk_deptno PRIMARY KEY (deptno)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE table_id_generate(
     digid BIGINT AUTO_INCREMENT COMMENT '主键管理ID',
     id_key VARCHAR(50) COMMENT '主键识别Key',
     id_value BIGINT(50) COMMENT '当前主键数据',
     CONSTRAINT pk_digid PRIMARY KEY (digid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 表table_id_generate初始化
INSERT INTO table_id_generate(id_key, id_value) VALUES ('COMPANY_ID', 3000);
INSERT INTO table_id_generate(id_key, id_value) VALUES ('DEPT_ID', 6666);
INSERT INTO table_id_generate(id_key, id_value) VALUES ('EMP_ID', 7777);