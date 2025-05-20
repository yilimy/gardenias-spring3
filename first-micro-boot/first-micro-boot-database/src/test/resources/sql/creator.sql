create table `member3` (
  `mid` varchar(50),
  `name` varchar(10),
  `age` int,
  `salary` double,
  `birthday` date,
  `content` text,
  `del` int default 0,
  constraint pk_mid  primary key (mid)
) engine=innodb default charset=utf8;

insert into member3 values ('m0001', '张三', 18, 5000, '2006-09-19', '["PHP", "Java", "C++"]', 0);
insert into member3 values ('m0002', '李四', 28, 6000, '1999-01-01', '["C++", "C#", "Python"]', 0);
insert into member3 values ('m0003', '王五', 38, 7000, '2004-08-13', '["C++", "C#", "Java"]', 0);