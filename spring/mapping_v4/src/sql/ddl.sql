
CREATE SCHEMA test;

CREATE TABLE test.users ( 
  id                   bigint  NOT NULL  AUTO_INCREMENT,
  fullname             varchar(255)    ,
  password             varchar(255)    ,
  username             varchar(255)    ,
  role_id              bigint  NOT NULL  ,
  CONSTRAINT pk_users PRIMARY KEY ( id )
 );

CREATE INDEX FKp56c1712k691lhsyewcssf40f ON test.users ( role_id );

ALTER TABLE test.users 
ADD CONSTRAINT `FKp56c1712k691lhsyewcssf40f` 
FOREIGN KEY ( role_id ) 
REFERENCES test.roles( id ) 
ON DELETE NO ACTION ON UPDATE NO ACTION;

CREATE TABLE test.roles ( 
  id                   bigint  NOT NULL  AUTO_INCREMENT,
  name                 varchar(255)    ,
  CONSTRAINT pk_roles PRIMARY KEY ( id )
 );






