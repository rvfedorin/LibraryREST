CREATE TABLE `books` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `cost` double DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `releaseDate` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `genre_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`));

INSERT INTO `books` VALUES (1,550,NULL,'1866-05-01T00:00','NOT_AVAILABLE','Crime and Punishment',3),(2,500,NULL,'1603-01-01T00:000','AVAILABLE','Hamlet',2),(3,1000,NULL,'1472-01-01T00:00','AVAILABLE','The Divine Comedy',1),(4,530,NULL,'2021-05-21T00:00','AVAILABLE','The Iliad',1),(5,600,NULL,'2021-05-18T00:00','AVAILABLE','The Odyssey',1),(6,1000,NULL,'1990-01-15T00:00','AVAILABLE','The Wheel of Time',1),(7,300,NULL,'1867-05-01T00:00','AVAILABLE','War and Peace',1);
