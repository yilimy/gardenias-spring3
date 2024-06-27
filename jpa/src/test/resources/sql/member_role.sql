CREATE TABLE `member_role` (
                               `mid` varchar(50) NOT NULL,
                               `rid` varchar(50) NOT NULL,
                               KEY `fk_mid` (`mid`),
                               KEY `fk_rid` (`rid`),
                               CONSTRAINT `fk_mid` FOREIGN KEY (`mid`) REFERENCES `member` (`mid`),
                               CONSTRAINT `fk_rid` FOREIGN KEY (`rid`) REFERENCES `role` (`rid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;