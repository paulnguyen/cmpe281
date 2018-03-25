
CREATE SCHEMA test;

CREATE TABLE test.customer ( 
	id                   bigint  NOT NULL  AUTO_INCREMENT,
	first_name           varchar(255)    ,
	last_name            varchar(255)    ,
	CONSTRAINT pk_customer PRIMARY KEY ( id )
 );

