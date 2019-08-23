package hellogorm

class Author2 {

	    static hasMany = [books:Book2]
	    String name

}


/*

GORM supports many-to-many relationships by defining a hasMany on 
both sides of the relationship and having a belongsTo on the owned 
side of the relationship:

	class Book {
	    static belongsTo = Author
	    static hasMany = [authors:Author]
	    String title
	}

	class Author {
	    static hasMany = [books:Book]
	    String name
	}

GORM maps a many-to-many using a join table at the database level. The owning 
side of the relationship, in this case Author, takes responsibility for 
persisting the relationship and is the only side that can cascade saves across.

For example this will work and cascade saves:

	new Author(name:"Stephen King")
	        .addToBooks(new Book(title:"The Stand"))
	        .addToBooks(new Book(title:"The Shining"))
	        .save()

However this will only save the Book and not the authors!

	new Book(name:"Groovy in Action")
	        .addToAuthors(new Author(name:"Dierk Koenig"))
	        .addToAuthors(new Author(name:"Guillaume Laforge"))
	        .save()

This is the expected behaviour as, just like Hibernate, only one side of a 
many-to-many can take responsibility for managing the relationship.


SQL DDL:

    create table author2 (
        id bigint not null auto_increment,
        version bigint not null,
        name varchar(255) not null,
        primary key (id)
    ) ENGINE=InnoDB

    create table book2 (
        id bigint not null auto_increment,
        version bigint not null,
        title varchar(255) not null,
        primary key (id)
    ) ENGINE=InnoDB
  
   create table author2_books (
        book2_id bigint not null,
        author2_id bigint not null,
        primary key (author2_id, book2_id)
    ) ENGINE=InnoDB
       
    alter table author2_books 
        add constraint FK6owugh3kbd0ss0v823m7ldqjs 
        foreign key (author2_id) 
        references author2 (id)
    
    alter table author2_books 
        add constraint FKs6a7orenpu4nmpfqw6jgrep5j 
        foreign key (book2_id) 
        references book2 (id)


*/