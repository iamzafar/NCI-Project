-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema sample
-- -----------------------------------------------------
-- It is used only for testing

-- -----------------------------------------------------
-- Schema sample
--
-- It is used only for testing
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `sample` DEFAULT CHARACTER SET utf8 ;
USE `sample` ;

-- -----------------------------------------------------
-- Table `sample`.`client`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sample`.`client` (
  `id` INT(12) NOT NULL AUTO_INCREMENT,
  `lastname` VARCHAR(45) NOT NULL,
  `firstname` VARCHAR(45) NOT NULL,
  `address` VARCHAR(50) NULL DEFAULT NULL,
  `city` VARCHAR(15) NULL DEFAULT NULL,
  `zipcode` VARCHAR(5) NULL DEFAULT NULL,
  `phone` VARCHAR(12) NULL DEFAULT NULL,
  `email` VARCHAR(30) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `sample`.`employee`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sample`.`employee` (
  `employeeId` INT(11) NOT NULL AUTO_INCREMENT,
  `last` VARCHAR(45) NOT NULL,
  `first` VARCHAR(45) NULL DEFAULT NULL,
  `amount` DOUBLE NULL DEFAULT NULL,
  `hourly` DECIMAL(4,2) NULL DEFAULT NULL,
  `cost` DECIMAL(5,2) NULL DEFAULT NULL,
  `position` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`employeeId`))
ENGINE = InnoDB
AUTO_INCREMENT = 145
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `sample`.`job`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sample`.`job` (
  `jobId` INT(11) NOT NULL AUTO_INCREMENT,
  `jobnum` VARCHAR(45) NOT NULL,
  `invoice` DECIMAL(6,2) NULL DEFAULT NULL,
  `cost` DECIMAL(6,2) NULL DEFAULT NULL,
  `t&m` DECIMAL(6,2) NULL DEFAULT NULL,
  `completion` VARCHAR(45) NULL DEFAULT 'NO',
  `worktype` VARCHAR(45) NULL DEFAULT NULL,
  `hours` DECIMAL(3,2) NULL DEFAULT NULL,
  `materials` DECIMAL(6,2) NULL DEFAULT NULL,
  `startdate` DATE NULL DEFAULT NULL,
  `finishdate` DATE NULL DEFAULT NULL,
  `client_id` INT(12) NOT NULL,
  `employeeId` INT(11) NOT NULL,
  PRIMARY KEY (`jobId`, `client_id`, `employeeId`),
  INDEX `fk_job_client_idx` (`client_id` ASC),
  INDEX `fk_job_employee1_idx` (`employeeId` ASC),
  CONSTRAINT `fk_job_client`
    FOREIGN KEY (`client_id`)
    REFERENCES `sample`.`client` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_job_employee1`
    FOREIGN KEY (`employeeId`)
    REFERENCES `sample`.`employee` (`employeeId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `sample`.`workorder`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sample`.`workorder` (
  `woID` INT(11) NOT NULL AUTO_INCREMENT,
  `date` DATE NULL DEFAULT NULL,
  `cost` DECIMAL(6,2) NULL,
  `name` VARCHAR(45) NULL DEFAULT NULL,
  `jobId` INT(11) NOT NULL,
  `client_id` INT(12) NOT NULL,
  PRIMARY KEY (`woID`, `jobId`, `client_id`),
  INDEX `fk_workorder_job1_idx` (`jobId` ASC, `client_id` ASC),
  CONSTRAINT `fk_workorder_job`
    FOREIGN KEY (`jobId` , `client_id`)
    REFERENCES `sample`.`job` (`jobId` , `client_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `sample`.`empl_work`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sample`.`empl_work` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `hours` DECIMAL(3,2) NULL,
  `total` DECIMAL(4,2) NULL,
  `employeeId` INT(11) NOT NULL,
  `woID` INT(11) NOT NULL,
  `jobId` INT(11) NOT NULL,
  `client_id` INT(12) NOT NULL,
  PRIMARY KEY (`ID`, `employeeId`, `woID`, `jobId`, `client_id`),
  INDEX `fk_empl_work_employee1_idx` (`employeeId` ASC),
  INDEX `fk_empl_work_workorder1_idx` (`woID` ASC, `jobId` ASC, `client_id` ASC),
  CONSTRAINT `fk_empl_work_employee1`
    FOREIGN KEY (`employeeId`)
    REFERENCES `sample`.`employee` (`employeeId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_empl_work_workorder1`
    FOREIGN KEY (`woID` , `jobId` , `client_id`)
    REFERENCES `sample`.`workorder` (`woID` , `jobId` , `client_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sample`.`tools`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sample`.`tools` (
  `idTools` INT NOT NULL DEFAULT 100,
  `type` VARCHAR(45) NULL,
  `each` VARCHAR(45) NULL,
  `cost` DECIMAL(5,2) NULL,
  `billed` DECIMAL(5,2) NULL,
  `name` VARCHAR(45) NULL,
  PRIMARY KEY (`idTools`))
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
