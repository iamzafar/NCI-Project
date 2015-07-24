CREATE TABLE `client` (
  `client_id` int(12) NOT NULL AUTO_INCREMENT,
  `lastname` varchar(45) NOT NULL,
  `firstname` varchar(45) NOT NULL,
  `address` varchar(50) DEFAULT NULL,
  `city` varchar(15) DEFAULT NULL,
  `zipcode` varchar(5) DEFAULT NULL,
  `phone` varchar(12) DEFAULT NULL,
  `email` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`client_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;