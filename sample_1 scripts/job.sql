CREATE TABLE `job` (
  `jobId` int(11) NOT NULL AUTO_INCREMENT,
  `jobnum` varchar(45) NOT NULL,
  `invoice` decimal(6,2) DEFAULT NULL,
  `jobcost` decimal(6,2) DEFAULT NULL,
  `t_m` decimal(6,2) DEFAULT NULL,
  `completion` varchar(45) DEFAULT 'NO',
  `worktype` varchar(45) DEFAULT NULL,
  `hours` decimal(3,2) DEFAULT NULL,
  `materials` decimal(6,2) DEFAULT NULL,
  `startdate` date DEFAULT NULL,
  `finishdate` date DEFAULT NULL,
  `client_id` int(12) NOT NULL,
  `employeeId` int(11) NOT NULL,
  PRIMARY KEY (`jobId`,`client_id`,`employeeId`),
  KEY `fk_job_client_idx` (`client_id`),
  KEY `fk_job_employee1_idx` (`employeeId`),
  CONSTRAINT `fk_job_client` FOREIGN KEY (`client_id`) REFERENCES `client` (`client_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_job_employee1` FOREIGN KEY (`employeeId`) REFERENCES `employee` (`employeeId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

