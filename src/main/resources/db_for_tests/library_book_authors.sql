CREATE TABLE `book_authors` (
  `book_id` bigint NOT NULL,
  `author_id` bigint NOT NULL,
  PRIMARY KEY (`book_id`,`author_id`));


INSERT INTO `book_authors` VALUES (2,1),(1,2),(3,3),(4,3),(5,3),(6,4),(7,5);
