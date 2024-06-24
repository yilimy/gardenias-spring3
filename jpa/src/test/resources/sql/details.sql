CREATE TABLE `details` (
                           `uid` VARCHAR ( 50 ) NOT NULL COMMENT '用户ID',
                           `name` VARCHAR ( 50 ) DEFAULT NULL COMMENT '用户姓名',
                           `age` INT COMMENT '用户年龄',
                           CONSTRAINT pk_uid2 PRIMARY KEY ( `uid` ),
                           CONSTRAINT fk_uid FOREIGN KEY ( uid ) REFERENCES login ( uid ) ON DELETE CASCADE
) ENGINE = INNODB DEFAULT CHARSET = utf8;