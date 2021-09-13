--
-- Table structure for table `addresses`
--

CREATE TABLE `addresses` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `apartmentNumber` int DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `houseNumber` int DEFAULT NULL,
  `street` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

--
-- Dumping data for table `addresses`
--

INSERT INTO `addresses` VALUES (1,11,'Orel',133,'M.Gorkogo'),(2,21,'Orel',133,'M.Gorkogo'),(3,21,'Orel',133,'Lenina');


