CREATE TABLE `workorder` (
  `woID` int(11) NOT NULL AUTO_INCREMENT,
  `date` date DEFAULT NULL,
  `cost` decimal(6,2) DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL,
  `jobId` int(11) NOT NULL,
  `client_id` int(12) NOT NULL,
  PRIMARY KEY (`woID`,`jobId`,`client_id`),
  KEY `fk_workorder_job1_idx` (`jobId`,`client_id`),
  CONSTRAINT `fk_workorder_job` FOREIGN KEY (`jobId`, `client_id`) REFERENCES `job` (`jobId`, `client_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


