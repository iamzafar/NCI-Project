CREATE TABLE `tools` (
  `idTools` int(11) NOT NULL AUTO_INCREMENT,
  `Type` varchar(45) DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL,
  `Each` varchar(45) DEFAULT NULL,
  `cost` double DEFAULT NULL,
  `billed` double DEFAULT NULL,
  PRIMARY KEY (`idTools`)
) ENGINE=InnoDB AUTO_INCREMENT=178 DEFAULT CHARSET=utf8;