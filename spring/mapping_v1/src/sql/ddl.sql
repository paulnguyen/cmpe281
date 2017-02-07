

CREATE SCHEMA test;

CREATE TABLE test.student ( 
	id                   bigint  NOT NULL  AUTO_INCREMENT,
	first_name           varchar(255)    ,
	last_name            varchar(255)    ,
	age                  int    ,
	CONSTRAINT pk_customer PRIMARY KEY ( id )
 );

