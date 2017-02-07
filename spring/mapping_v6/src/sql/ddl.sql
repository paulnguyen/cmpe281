
CREATE SCHEMA test;

CREATE TABLE test.articles ( 
	id                   bigint  NOT NULL  AUTO_INCREMENT,
	content              varchar(255)    ,
	title                varchar(255)    ,
	CONSTRAINT pk_articles PRIMARY KEY ( id )
 );

CREATE TABLE test.users ( 
	id                   bigint  NOT NULL  AUTO_INCREMENT,
	fullname             varchar(255)    ,
	password             varchar(255)    ,
	username             varchar(255)    ,
	CONSTRAINT pk_users PRIMARY KEY ( id )
 );

CREATE TABLE test.users_vote_articles ( 
	article_id           bigint  NOT NULL  ,
	user_id              bigint  NOT NULL  ,
	vote_type            varchar(255)    ,
	CONSTRAINT pk_users_vote_articles PRIMARY KEY ( article_id, user_id )
 );

CREATE INDEX FKdncvyhodja0ecd56g6bllsem5 ON test.users_vote_articles ( user_id );

ALTER TABLE test.users_vote_articles 
	ADD CONSTRAINT `FKpb82rn4hy9cgccl51vru44bgt` FOREIGN KEY ( article_id ) 
	REFERENCES test.articles( id ) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE test.users_vote_articles 
	ADD CONSTRAINT `FKdncvyhodja0ecd56g6bllsem5` FOREIGN KEY ( user_id ) 
	REFERENCES test.users( id ) ON DELETE NO ACTION ON UPDATE NO ACTION;





