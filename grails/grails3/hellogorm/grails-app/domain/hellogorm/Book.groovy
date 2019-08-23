package hellogorm

/* 

	Table/Column Mappings

	Class Book 				-> Table:  book
	Attribute title 		-> Column: title
	Attribute releaseDate	-> Column: release_date
	Attribute ISBN			-> Column: isbn

*/

class Book {

    String title
    Date releaseDate
    String ISBN

    static constraints = {
    }
}


/*

SQL DDL:

    create table book (
        id bigint not null auto_increment,
        version bigint not null,
        isbn varchar(255) not null,
        release_date datetime not null,
        title varchar(255) not null,
        primary key (id)
    ) ENGINE=InnoDB

*/