
-- -----------------------------------------------------
-- Schema Definition
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cmpe281`.`TASK` ;

CREATE TABLE IF NOT EXISTS `cmpe281`.`TASK` (
  `ID` INT NOT NULL,
  `DESC` VARCHAR(45) NULL,
  `OWNER` VARCHAR(45) NULL,
  `PLAN_START` DATETIME NULL,
  `PLAN_FINISH` DATETIME NULL,
  `DONE` VARCHAR(1) NULL COMMENT 'T/F')
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `cmpe281`.`CARD`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cmpe281`.`CARD` ;

CREATE TABLE IF NOT EXISTS `cmpe281`.`CARD` (
  `ID` INT NOT NULL,
  `DESC` VARCHAR(45) NULL,
  `CYCLE_TIME` INT NULL COMMENT '# Days')
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `cmpe281`.`STORY`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cmpe281`.`STORY` ;

CREATE TABLE IF NOT EXISTS `cmpe281`.`STORY` (
  `ID` INT NOT NULL,
  `DESC` VARCHAR(45) NULL,
  `HOURS` INT NULL COMMENT 'Hours Remaining',
  `DONE` VARCHAR(1) NULL COMMENT 'T/F')
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `cmpe281`.`TENANT_FIELDS`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cmpe281`.`TENANT_FIELDS` ;

CREATE TABLE IF NOT EXISTS `cmpe281`.`TENANT_FIELDS` (
  `TENANT_ID` VARCHAR(10) NOT NULL,
  `TABLE_NAME` VARCHAR(45) NOT NULL,
  `FIELD_NAME` VARCHAR(45) NOT NULL,
  `FIELD_TYPE` VARCHAR(80) NULL,
  `FIELD_COLUMN` INT NOT NULL,
  PRIMARY KEY (`TENANT_ID`, `TABLE_NAME`, `FIELD_NAME`, `FIELD_COLUMN`),
  CONSTRAINT `fk_TENANT_FIELDS_TENANT_TABLE`
    FOREIGN KEY (`TENANT_ID` , `TABLE_NAME`)
    REFERENCES `cmpe281`.`TENANT_TABLE` (`TENANT_ID` , `TABLE_NAME`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `cmpe281`.`TENANT_TABLE`
-- -----------------------------------------------------
SET foreign_key_checks = 0;
DROP TABLE IF EXISTS `cmpe281`.`TENANT_TABLE` ;
SET foreign_key_checks = 1;

CREATE TABLE IF NOT EXISTS `cmpe281`.`TENANT_TABLE` (
  `TENANT_ID` VARCHAR(10) NOT NULL,
  `TABLE_NAME` VARCHAR(45) NOT NULL,
  `TABLE_DESC` VARCHAR(80) NULL,
  PRIMARY KEY (`TENANT_ID`, `TABLE_NAME`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `cmpe281`.`TENANT_DATA`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cmpe281`.`TENANT_DATA` ;

CREATE TABLE IF NOT EXISTS `cmpe281`.`TENANT_DATA` (
  `RECORD_ID` VARCHAR(45) NOT NULL,
  `TENANT_ID` VARCHAR(10) NOT NULL,
  `TENANT_TABLE` VARCHAR(45) NULL,
  `COLUMN_1` VARCHAR(80) NULL,
  `COLUMN_2` VARCHAR(80) NULL,
  `COLUMN_3` VARCHAR(80) NULL,
  `COLUMN_4` VARCHAR(80) NULL,
  `COLUMN_5` VARCHAR(80) NULL,
  `COLUMN_6` VARCHAR(80) NULL,
  `COLUMN_7` VARCHAR(80) NULL,
  `COLUMN_8` VARCHAR(80) NULL,
  `COLUMN_9` VARCHAR(80) NULL,
  `COLUMN_10` VARCHAR(80) NULL,
  PRIMARY KEY (`TENANT_ID`, `RECORD_ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Metadata Example for TASK
-- -----------------------------------------------------

-- TENANT TABLE
insert into TENANT_TABLE( TENANT_ID, TABLE_NAME, TABLE_DESC )
values ( 'TA', 'TASK', 'Task Table for Waterfall' ) ;

-- TENANT FIELDS
insert into TENANT_FIELDS( TENANT_ID, TABLE_NAME, FIELD_NAME, FIELD_TYPE, FIELD_COLUMN ) 
  values ( 'TA', 'TASK', 'ID', 'INT', 1 ) ;
insert into TENANT_FIELDS( TENANT_ID, TABLE_NAME, FIELD_NAME, FIELD_TYPE, FIELD_COLUMN ) 
  values ( 'TA', 'TASK', 'DESC', 'VARCHAR(45)', 2 ) ;
insert into TENANT_FIELDS( TENANT_ID, TABLE_NAME, FIELD_NAME, FIELD_TYPE, FIELD_COLUMN ) 
  values ( 'TA', 'TASK', 'OWNER', 'VARCHAR(45)', 3 ) ;
insert into TENANT_FIELDS( TENANT_ID, TABLE_NAME, FIELD_NAME, FIELD_TYPE, FIELD_COLUMN ) 
  values ( 'TA', 'TASK', 'PLAN_START', 'DATETIME', 4 ) ;
insert into TENANT_FIELDS( TENANT_ID, TABLE_NAME, FIELD_NAME, FIELD_TYPE, FIELD_COLUMN ) 
  values ( 'TA', 'TASK', 'PLAN_FINISH', 'DATETIME', 5 ) ;
insert into TENANT_FIELDS( TENANT_ID, TABLE_NAME, FIELD_NAME, FIELD_TYPE, FIELD_COLUMN ) 
  values ( 'TA', 'TASK', 'DONE', 'VARCHAR(1)', 6 ) ;

-- QUERY TABLE DEFINITION
select T.TABLE_NAME, F.FIELD_NAME, F.FIELD_TYPE, F.FIELD_COLUMN
from TENANT_TABLE T, TENANT_FIELDS F
where T.TENANT_ID = F.TENANT_ID
and T.TENANT_ID = 'TA'
and T.TABLE_NAME = 'TASK'
order by F.FIELD_COLUMN

-- TASK TABLE Record Example
insert into TASK( ID, `DESC`, OWNER, PLAN_START,  PLAN_FINISH, DONE)
values ( 1, 'Task Table for Waterfall', 'Paul', '2014-09-01', '2014-10-01', 'F' ) ;

-- TENANT_DATA Record Example
insert into TENANT_DATA (RECORD_ID, TENANT_ID, TENANT_TABLE, 
            COLUMN_1, COLUMN_2, COLUMN_3, COLUMN_4, COLUMN_5, COLUMN_6  ) 
values ( '1001', 'TA', 'TASK', 
         '1', 'Design Data Models', 'Paul', '2014-09-01', '2014-10-01', 'F' ) ;

-- Test Queries
select * from TASK ;
select * from TENANT_DATA where TENANT_ID = 'TA' and TENANT_TABLE = 'TASK' ;

-- Column Header Query
select  T.TABLE_NAME, 
        C1.FIELD_NAME, C2.FIELD_NAME, C3.FIELD_NAME,
        C4.FIELD_NAME, C5.FIELD_NAME, C6.FIELD_NAME
from  TENANT_TABLE T,
    TENANT_FIELDS C1,
    TENANT_FIELDS C2,
    TENANT_FIELDS C3,
    TENANT_FIELDS C4,
    TENANT_FIELDS C5,
    TENANT_FIELDS C6
where   T.TENANT_ID = 'TA' 
and     T.TABLE_NAME = 'TASK'
and     T.TABLE_NAME = C1.TABLE_NAME and T.TENANT_ID = C1.TENANT_ID and C1.FIELD_COLUMN = 1
and     T.TABLE_NAME = C2.TABLE_NAME and T.TENANT_ID = C2.TENANT_ID and C2.FIELD_COLUMN = 2
and     T.TABLE_NAME = C3.TABLE_NAME and T.TENANT_ID = C3.TENANT_ID and C3.FIELD_COLUMN = 3
and     T.TABLE_NAME = C4.TABLE_NAME and T.TENANT_ID = C4.TENANT_ID and C4.FIELD_COLUMN = 4
and     T.TABLE_NAME = C5.TABLE_NAME and T.TENANT_ID = C5.TENANT_ID and C5.FIELD_COLUMN = 5
and     T.TABLE_NAME = C6.TABLE_NAME and T.TENANT_ID = C6.TENANT_ID and C6.FIELD_COLUMN = 6
UNION
select TENANT_TABLE, COLUMN_1, COLUMN_2, COLUMN_3, COLUMN_4, COLUMN_5, COLUMN_6 
from   TENANT_DATA
where  TENANT_ID = 'TA'
and TENANT_TABLE = 'TASK' ;

