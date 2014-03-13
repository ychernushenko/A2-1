unlock tables;
DROP DATABASE IF exists `users`;
create database users;
use users;

DROP TABLE IF EXISTS `user_Info`;

CREATE TABLE `user_Info` (
	`user_id` varchar(10) primary key,
	`pw` varchar(100),
	`accessability` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `login_record`;
CREATE TABLE `login_record` (
	`user_id` varchar(10),
	`activity` varchar(10),
	`time` datetime,
	primary key (`user_id`, `time`),
	foreign key (`user_id`) references `user_Info`(`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


LOCK TABLES `user_Info` WRITE;

INSERT INTO `user_Info` VALUES ('super', PASSWORD('super'),'ios'), ('admin', PASSWORD('admin'),'i'), ('user', PASSWORD('user'),'os');

unlock tables;