
create database cmpe281 ;
use cmpe281 ;

CREATE TABLE gumball (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  version bigint(20) NOT NULL,
  count_gumballs int(11) NOT NULL,
  model_number varchar(255) NOT NULL,
  serial_number varchar(255) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY serial_number (serial_number)
) ;

insert into gumball ( id, version, count_gumballs, model_number, serial_number ) 
values ( 1, 0, 1000, 'M102988', '1234998871109' ) ;

select * from gumball ;