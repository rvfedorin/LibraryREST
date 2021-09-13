CREATE TABLE `genres` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

INSERT INTO `genres` VALUES (1,'Novel'),(2,'Crime'),(3,'Tragedy'),(4,'Comedy');
