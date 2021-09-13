CREATE TABLE `authors` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `firstName` varchar(255) DEFAULT NULL,
  `lastName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);


INSERT INTO `authors` VALUES (1,'William','Shakespeare'),(2,'Fyodor','Dostoevsky'),(3,'Homer',NULL),(4,'Robert','Jordan'),(5,'Leo','Tolstoy'),(6,'David','Crane'),(7,'Marta','Caufman');

