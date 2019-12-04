CREATE DATABASE  IF NOT EXISTS `cashreg`;
USE `cashreg`;

DROP TABLE IF EXISTS `user_type`;
CREATE TABLE `user_type` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `type` varchar(45) NOT NULL,
  `description` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `user_type` VALUES (1,'admin','Admin'),(2,'senior_cashier','Senior Cashier'),(3,'cashier','Cahier'),(4,'goods_spec','Adder goods');

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `id_user_type` bigint(20) NOT NULL,
  `login` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_user_type_idx` (`id_user_type`),
  CONSTRAINT `id_user_type` FOREIGN KEY (`id_user_type`) REFERENCES `user_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `user` VALUES (1,4,'adder@gmail.com','9371bdcfd91f1da2db3465490aad8d5','Adder'),(2,3,'scashier@gmail.com','2175f2caf5acd11cf3e31a8614f9cd9','Senior Cashier'),(3,2,'cashier@gmail.com','6ac247ed8ccf24fd5ff89b32a355cf','Cashier');

DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` int(4) unsigned NOT NULL,
  `name` varchar(255) NOT NULL,
  `quant` double unsigned zerofill NOT NULL,
  `price` double NOT NULL DEFAULT '0',
  `measure` varchar(45) NOT NULL,
  `comments` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code_UNIQUE` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `goods` VALUES (1,1,'Coca-Cola',10000,20,'кг',''),(2,2,'Sprite',10000,30,'кг',''),(3,3,'Pepsi',10000,25,'кг',''),(4,4,'Miranda',10000,17,'кг',''),(5,5,'7Up',10000,24,'кг',''),(6,6,'Sandora',10000,33,'кг',''),(7,7,'Nesquick',10000,40,'кг',''),(8,8,'Mars',10000,16,'кг',''),(9,9,'Sneackers',10000,18,'кг',''),(10,10,'Bounty',10000,15,'кг',''),(11,11,'Twix',10000,22,'кг',''),(12,12,'Milky way',10000,11,'кг','');

DROP TABLE IF EXISTS `check`;
CREATE TABLE `check` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `creator` bigint(20) NOT NULL,
  `crtime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `total` double NOT NULL DEFAULT '0',
  `discount` double NOT NULL DEFAULT '0',
  `canceled` tinyint(1) NOT NULL DEFAULT '0',
  `registration` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `creator_idx` (`creator`),
  CONSTRAINT `creator` FOREIGN KEY (`creator`) REFERENCES `user` (`id`),
  CONSTRAINT `canceled` CHECK (((`canceled` = 0) or (`canceled` = 1)))
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `checkspec`;
CREATE TABLE `checkspec` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `id_check` bigint(20) NOT NULL,
  `id_good` bigint(20) NOT NULL,
  `quant` double NOT NULL DEFAULT '0',
  `price` double NOT NULL DEFAULT '0',
  `total` double NOT NULL DEFAULT '0',
  `nds` int(3) DEFAULT '0',
  `ndstotal` double NOT NULL DEFAULT '0',
  `canceled` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `id_check_idx` (`id_check`),
  KEY `id_good_idx` (`id_good`),
  CONSTRAINT `id_check` FOREIGN KEY (`id_check`) REFERENCES `check` (`id`) ON DELETE CASCADE,
  CONSTRAINT `id_good` FOREIGN KEY (`id_good`) REFERENCES `goods` (`id`),
  CONSTRAINT `canceled_spec` CHECK (((`canceled` = 0) or (`canceled` = 1)))
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `fiscal`;
CREATE TABLE `fiscal` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `total` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
