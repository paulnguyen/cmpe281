-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema model
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema test
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema test
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `test` DEFAULT CHARACTER SET latin1 ;
USE `test` ;

-- -----------------------------------------------------
-- Table `test`.`customer`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `test`.`customer` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(255) NULL DEFAULT NULL,
  `first_name` VARCHAR(255) NULL DEFAULT NULL,
  `last_name` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `UK_dwk6cx0afu8bs9o4t536v1j5v` (`email` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `test`.`address`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `test`.`address` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `city` VARCHAR(255) NULL DEFAULT NULL,
  `country` VARCHAR(255) NULL DEFAULT NULL,
  `street` VARCHAR(255) NULL DEFAULT NULL,
  `customer_id` BIGINT(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK93c3js0e22ll1xlu21nvrhqgg` (`customer_id` ASC),
  CONSTRAINT `FK93c3js0e22ll1xlu21nvrhqgg`
    FOREIGN KEY (`customer_id`)
    REFERENCES `test`.`customer` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `test`.`product`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `test`.`product` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `description` VARCHAR(255) NULL DEFAULT NULL,
  `name` VARCHAR(255) NOT NULL,
  `price` DECIMAL(19,2) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `test`.`orders`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `test`.`orders` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `billing_address_id` BIGINT(20) NULL DEFAULT NULL,
  `customer_id` BIGINT(20) NOT NULL,
  `shipping_address_id` BIGINT(20) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `FKqraecqgbbr1p37ic9dr44e2dr` (`billing_address_id` ASC),
  INDEX `FK624gtjin3po807j3vix093tlf` (`customer_id` ASC),
  INDEX `FKh0uue95ltjysfmkqb5abgk7tj` (`shipping_address_id` ASC),
  CONSTRAINT `FK624gtjin3po807j3vix093tlf`
    FOREIGN KEY (`customer_id`)
    REFERENCES `test`.`customer` (`id`),
  CONSTRAINT `FKh0uue95ltjysfmkqb5abgk7tj`
    FOREIGN KEY (`shipping_address_id`)
    REFERENCES `test`.`address` (`id`),
  CONSTRAINT `FKqraecqgbbr1p37ic9dr44e2dr`
    FOREIGN KEY (`billing_address_id`)
    REFERENCES `test`.`address` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `test`.`line_item`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `test`.`line_item` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `amount` INT(11) NOT NULL,
  `price` DECIMAL(19,2) NOT NULL,
  `product_id` BIGINT(20) NULL DEFAULT NULL,
  `order_id` BIGINT(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK237t8tbj9haibqe7wafj4t54x` (`product_id` ASC),
  INDEX `FKlfuo9o3keu9a7mlxumaqoylgu` (`order_id` ASC),
  CONSTRAINT `FK237t8tbj9haibqe7wafj4t54x`
    FOREIGN KEY (`product_id`)
    REFERENCES `test`.`product` (`id`),
  CONSTRAINT `FKlfuo9o3keu9a7mlxumaqoylgu`
    FOREIGN KEY (`order_id`)
    REFERENCES `test`.`orders` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `test`.`product_attributes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `test`.`product_attributes` (
  `product_id` BIGINT(20) NOT NULL,
  `attributes` VARCHAR(255) NULL DEFAULT NULL,
  `attributes_key` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`product_id`, `attributes_key`),
  CONSTRAINT `FK95nqsy6pr19opyu092fbwdr74`
    FOREIGN KEY (`product_id`)
    REFERENCES `test`.`product` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
