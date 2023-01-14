-- Database Script for Pitos web project

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


CREATE TABLE `role` (	
	`role_id` int NOT NULL,
    `description` varchar(45) NOT NULL,
    PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(100) NOT NULL,
  `fullName` varchar(200) NOT NULL,
  `email` varchar(200) NOT NULL,
  `address` varchar(200) NOT NULL,
  `tel` varchar(50) default null,
  `status` varchar(50),
  `code` varchar(50) default null,
  `session` varchar(50) default null,
  `salt` varchar(50) default "abcd1234",
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `user_role` (
	`user_id` int not null,
    `role_id` int not null,
    KEY `user_id_fk_user` (`user_id`),
    KEY `role_id_fk_role` (`role_id`),
	CONSTRAINT `id_fk_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE,
	CONSTRAINT `role_id_fk_role` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`)  ON DELETE CASCADE
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
  `user_id` int,
  PRIMARY KEY (`id`),
  KEY `order_fk_area` (`area_id`),
  KEY `user_id_fk_user` (`user_id`),
  CONSTRAINT `order_fk_area` FOREIGN KEY (`area_id`) REFERENCES `area` (`id`),
  CONSTRAINT `user_id_fk_user` FOREIGN KEY(`user_id`) REFERENCES `user` (`id`)
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
 
-- INSERTING PIES
 
insert into pie(id, name, price, filename, ingredients)
values (1, "Σπανακόπιτα", 4, "/images/spanakopita.jpg", "Σπανάκι, Φέτα" );

insert into pie(id, name, price, filename, ingredients)
values (2, "Μανιταρόπιτα", 5.5, "/images/manitaropita.jpg", "Μανιτάρια, Βούτυρο");

insert into pie(id, name, price, filename, ingredients)
values (3, "Πρασόπιτα", 3.5, "/images/prasopita.jpg", "Πράσο, Φέτα" );

insert into pie(id, name, price, filename, ingredients)
values (4, "Μπουρέκι", 4, "/images/boureki.jpg", "Κολοκύθι, Πατάτα" );

-- INSERTING AREAS

insert into area(id, description)
values (1, "Αμπελόκηποι");

insert into area(id, description)
values (2, "Παπάγου");

insert into area(id, description)
values (3, "Κέντρο Αθήνας");

insert into area(id, description)
values (4, "Ζωγράφου");

-- INSERTING ROLES

insert into role(role_id, description)
values(1, "admin");

insert into role(role_id, description)
values(2, "customer");

-- INSERTING DUMMY USERS / ADMINS

insert into user(id, username, password, address, fullname, email, status, salt) -- password = "123456"
values(1, "bob", "423389c5f680c0a71c246fa64ba52d47fc1a7a9531d5f198fd408ba42afba55d", "Nowhere","Bob Dole" , "bob@dole.com", "verified", "hUifhkJ4jkyOye74KPusaA==");

insert into user(id, username, password, address, fullname, email, status, salt) -- password = "123456"
values(2, "tom", "ff20754904bc12cdc82383593793dd164e4adc4c39294bf9c0fc1daabf47cf57", "Nowhere","Tom Hanks", "tom@hanks.com", "verified", "FmL3wqNEo7+FxMSiN6fDkQ==");

-- INSERTING USER ROLES

insert into user_role(user_id, role_id)
values(1, 1);

insert into user_role(user_id, role_id)
values(2, 2);
