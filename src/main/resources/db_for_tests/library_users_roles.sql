CREATE TABLE `users_roles` (
  `user_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`));

INSERT INTO `users_roles` VALUES (1,1),(2,1),(1,2);
