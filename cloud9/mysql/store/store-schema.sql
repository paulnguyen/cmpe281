SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';


-- -----------------------------------------------------
-- Table `Customers`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Customers` (
  `CustomerID` INT  NOT NULL AUTO_INCREMENT,
  `CustomerName` VARCHAR(255) NULL,
  `CustomerContact` VARCHAR(255) NULL,
  `Address` VARCHAR(500) NULL,
  `City` VARCHAR(255) NULL,
  `PostalCode` VARCHAR(45) NULL,
  `Country` VARCHAR(45) NULL,
  PRIMARY KEY (`CustomerID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Employees`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Employees` (
  `EmployeeID` INT NOT NULL,
  `LastName` VARCHAR(255) NULL,
  `FirstName` VARCHAR(255) NULL,
  `BirthDate` DATE NULL,
  `Photo` LONGBLOB NULL,
  `Notes` LONGTEXT NULL,
  PRIMARY KEY (`EmployeeID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Suppliers`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Suppliers` (
  `SupplierID` INT NOT NULL,
  `SupplierName` VARCHAR(255) NULL,
  `ContactName` VARCHAR(255) NULL,
  `Address` VARCHAR(500) NULL,
  `City` VARCHAR(45) NULL,
  `PostalCode` VARCHAR(45) NULL,
  `Country` VARCHAR(45) NULL,
  `Phone` VARCHAR(45) NULL,
  PRIMARY KEY (`SupplierID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Shippers`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Shippers` (
  `ShipperID` INT NOT NULL,
  `ShipperName` VARCHAR(255) NULL,
  `Phone` VARCHAR(45) NULL,
  PRIMARY KEY (`ShipperID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Categories`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Categories` (
  `CategoryID` INT NOT NULL,
  `Categoryname` VARCHAR(45) NULL,
  `Description` VARCHAR(255) NULL,
  PRIMARY KEY (`CategoryID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Products`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Products` (
  `ProductID` INT NOT NULL,
  `ProductName` VARCHAR(45) NULL,
  `SupplierID` INT NULL,
  `CategoryID` INT NULL,
  `Unit` VARCHAR(255) NULL,
  `Price` DECIMAL(12,2) NULL,
  PRIMARY KEY (`ProductID`),
  INDEX `fk_Products_Categories_idx` (`CategoryID` ASC),
  INDEX `fk_Products_Suppliers1_idx` (`SupplierID` ASC),
  CONSTRAINT `fk_Products_Categories`
    FOREIGN KEY (`CategoryID`)
    REFERENCES `Categories` (`CategoryID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Products_Suppliers1`
    FOREIGN KEY (`SupplierID`)
    REFERENCES `Suppliers` (`SupplierID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Orders`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Orders` (
  `OrderID` INT NOT NULL,
  `CustomerID` INT NULL,
  `EmployeeID` INT NULL,
  `OrderDate` DATETIME NULL,
  `ShipperID` INT NULL,
  PRIMARY KEY (`OrderID`),
  INDEX `fk_Orders_Customers1_idx` (`CustomerID` ASC),
  INDEX `fk_Orders_Employees1_idx` (`EmployeeID` ASC),
  INDEX `fk_Orders_Shippers1_idx` (`ShipperID` ASC),
  CONSTRAINT `fk_Orders_Customers1`
    FOREIGN KEY (`CustomerID`)
    REFERENCES `Customers` (`CustomerID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Orders_Employees1`
    FOREIGN KEY (`EmployeeID`)
    REFERENCES `Employees` (`EmployeeID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Orders_Shippers1`
    FOREIGN KEY (`ShipperID`)
    REFERENCES `Shippers` (`ShipperID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `OrderDetails`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `OrderDetails` (
  `OrderDetailID` INT NOT NULL,
  `OrderID` INT NULL,
  `ProductID` INT NULL,
  `Quantity` INT NULL,
  PRIMARY KEY (`OrderDetailID`),
  INDEX `fk_OrderDetails_Orders1_idx` (`OrderID` ASC),
  INDEX `fk_OrderDetails_Products1_idx` (`ProductID` ASC),
  CONSTRAINT `fk_OrderDetails_Orders1`
    FOREIGN KEY (`OrderID`)
    REFERENCES `Orders` (`OrderID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_OrderDetails_Products1`
    FOREIGN KEY (`ProductID`)
    REFERENCES `Products` (`ProductID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;
