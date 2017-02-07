
CREATE SCHEMA test;

CREATE TABLE test.permissions ( 
	id                   bigint  NOT NULL  AUTO_INCREMENT,
	description          varchar(255)    ,
	name                 varchar(255)    ,
	CONSTRAINT pk_permissions PRIMARY KEY ( id )
 );

CREATE TABLE test.roles ( 
	id                   bigint  NOT NULL  AUTO_INCREMENT,
	name                 varchar(255)    ,
	CONSTRAINT pk_roles PRIMARY KEY ( id )
 );

CREATE TABLE test.roles_permissions ( 
	role_id              bigint  NOT NULL  ,
	permission_id        bigint  NOT NULL  
 );

CREATE INDEX FKbx9r9uw77p58gsq4mus0mec0o ON test.roles_permissions ( permission_id );

CREATE INDEX FKqi9odri6c1o81vjox54eedwyh ON test.roles_permissions ( role_id );

ALTER TABLE test.roles_permissions 
	ADD CONSTRAINT `FKbx9r9uw77p58gsq4mus0mec0o` FOREIGN KEY ( permission_id ) 
	REFERENCES test.permissions( id ) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE test.roles_permissions 
	ADD CONSTRAINT `FKqi9odri6c1o81vjox54eedwyh` FOREIGN KEY ( role_id ) 
	REFERENCES test.roles( id ) ON DELETE NO ACTION ON UPDATE NO ACTION;



