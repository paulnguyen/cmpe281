
CREATE SCHEMA test;

CREATE TABLE test.accounts ( 
  id                   bigint  NOT NULL  AUTO_INCREMENT,
  fullname             varchar(255)    ,
  password             varchar(255)    ,
  username             varchar(255)    ,
  CONSTRAINT pk_accounts PRIMARY KEY ( id )
 );

CREATE TABLE test.user_profiles ( 
  id                   bigint  NOT NULL  ,
  address              varchar(255)    ,
  age                  int    ,
  email_address        varchar(255)    ,
  first_name           varchar(255)    ,
  last_name            varchar(255)    ,
  phone_number         varchar(255)    ,
  CONSTRAINT pk_user_profiles PRIMARY KEY ( id )
 );




