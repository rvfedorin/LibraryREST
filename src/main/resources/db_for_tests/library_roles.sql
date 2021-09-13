CREATE TABLE `roles` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ;

INSERT INTO `roles` VALUES (1,'ROLE_USER'),(2,'ROLE_ADMIN');
