CREATE TABLE `employee` (
  `employeeId` int(11) NOT NULL AUTO_INCREMENT,
  `last` varchar(45) NOT NULL,
  `first` varchar(45) DEFAULT NULL,
  `amount` double DEFAULT NULL,
  `hourly` decimal(4,2) DEFAULT NULL,
  `cost` decimal(5,2) DEFAULT NULL,
  `position` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`employeeId`)
) ENGINE=InnoDB AUTO_INCREMENT=146 DEFAULT CHARSET=utf8;