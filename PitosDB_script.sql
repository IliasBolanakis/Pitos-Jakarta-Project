-- Script for Pitos project database

drop database if exists pitosdb;
create database pitosdb;

use pitosdb;
CREATE TABLE `pie` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(200) NOT NULL,
  `price` double NOT NULL,
  `filename` varchar(200) NOT NULL,
  `ingredients` varchar(200) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `area` (
  `id` int NOT NULL,
  `description` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `order` (
  `id` int NOT NULL AUTO_INCREMENT,
  `fullname` varchar(200) NOT NULL,
  `address` varchar(200) NOT NULL,
  `area_id` int NOT NULL,
  `email` varchar(50) DEFAULT NULL,
  `tel` varchar(20) NOT NULL,
  `comments` varchar(200) DEFAULT NULL,
  `offer` tinyint DEFAULT NULL,
  `payment` varchar(20) DEFAULT NULL,
  `stamp` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `order_fk_area` (`area_id`),
  CONSTRAINT `order_fk_area` FOREIGN KEY (`area_id`) REFERENCES `area` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `order_item` (
  `id` int NOT NULL AUTO_INCREMENT,
  `order_id` int NOT NULL,
  `pie_id` int NOT NULL,
  `quantity` int NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `order_item_fk_order` (`order_id`),
  KEY `order_item_fk_pie` (`pie_id`),
  CONSTRAINT `order_item_fk_order` FOREIGN KEY (`order_id`) REFERENCES `order` (`id`),
  CONSTRAINT `order_item_fk_pie` FOREIGN KEY (`pie_id`) REFERENCES `pie` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `role` (	
	`role_id` int NOT NULL AUTO_INCREMENT,
    `description` varchar(45) NOT NULL auto_increment,
    PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `fullname` varchar(200) NOT NULL,
  `email` varchar(200) NOT NULL,
  `tel` varchar(50) NOT NULL,
  `status` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

insert into pie(id, name, price, filename, ingredients)
values (1, "Σπανακοπιτα", 4, "/images/spanakopita.jpg", "Σπανάκι, Φέτα" );

insert into pie(id, name, price, filename, ingredients)
values (2, "Μανιταρόπιτα", 5.5, "/images/manitaropita.jpg", "Μανιτάρια, Βούτυρο");

insert into pie(id, name, price, filename, ingredients)
values (3, "Πρασόπιτα", 3.5, "/images/prasopita.jpg", "Πράσο, Φέτα" );

insert into pie(id, name, price, filename, ingredients)
values (4, "Μπουρέκι", 4, "/images/boureki.jpg", "Κολοκύθι, Πατάτα" );

insert into area(id, description)
values (1, "Αμπελόκηποι");

insert into area(id, description)
values (2, "Παπάγου");

insert into area(id, description)
values (3, "Κέντρο Αθήνας");

insert into area(id, description)
values (4, "Ζωγράφου");