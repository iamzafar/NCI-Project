CREATE TABLE `empl_work` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `hours` decimal(3,2) DEFAULT NULL,
  `total` decimal(4,2) DEFAULT NULL,
  `employeeId` int(11) NOT NULL,
  `woID` int(11) NOT NULL,
  `jobId` int(11) NOT NULL,
  `client_id` int(12) NOT NULL,
  PRIMARY KEY (`ID`,`employeeId`,`woID`,`jobId`,`client_id`),
  KEY `fk_empl_work_employee1_idx` (`employeeId`),
  KEY `fk_empl_work_workorder1_idx` (`woID`,`jobId`,`client_id`),
  CONSTRAINT `fk_empl_work_employee1` FOREIGN KEY (`employeeId`) REFERENCES `employee` (`employeeId`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_empl_work_workorder1` FOREIGN KEY (`woID`, `jobId`, `client_id`) REFERENCES `workorder` (`woID`, `jobId`, `client_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;