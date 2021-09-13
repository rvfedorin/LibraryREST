CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `firstName` varchar(255) DEFAULT NULL,
  `lastName` varchar(255) DEFAULT NULL,
  `login` varchar(255) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `address_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `login` (`login`));

INSERT INTO `users` VALUES (1,NULL,'Admin','pass:test','rf','$2a$04$CkuamQFggeSEZZ7jQKdn4ujurtCWD/VWBNHUO7Xe15H4DiamW6EZS',1),(2,NULL,'User','pass:pass','login','$2a$04$zYtMfehAeMguqWkKgZ/ZK.1gVJuYrftcemvaDcDZN/v8ZG.ddx6aa',2);
