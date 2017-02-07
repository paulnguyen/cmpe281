

CREATE SCHEMA test;

CREATE TABLE test.account ( 
  id                   bigint  NOT NULL  AUTO_INCREMENT,
  fullname             varchar(255)    ,
  password             varchar(255)    ,
  state                int    ,
  username             varchar(255)    ,
  CONSTRAINT pk_account PRIMARY KEY ( id )
 );

