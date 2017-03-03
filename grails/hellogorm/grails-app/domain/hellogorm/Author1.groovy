package hellogorm

class Author1 {

    static hasMany = [books: Book1]
    String name

    static constraints = {
    }

}


/*

A one-to-many relationship is when one class, example Author, has many instances of another class, example Book. 
With GORM you define such a relationship with the hasMany.

	class Author {
	    static hasMany = [books: Book]

	    String name
	}

	class Book {
	    String title
	}

GORM will automatically inject a property of type java.util.Set into the domain class based 
on the hasMany setting. This can be used to iterate over the collection:

	def a = Author.get(1)

	for (book in a.books) {
	    println book.title
	}

The default fetch strategy used by GORM is "lazy", which means that the collection will be 
lazily initialized on first access. This can lead to the N+1 if you are not careful.

NOTE (N+1 Problerm): https://secure.phabricator.com/book/phabcontrib/article/n_plus_one/

If you need "eager" fetching you can use the ORM DSL or specify eager fetching as part
of a query.

SQL DDL:

    create table author1 (
        id bigint not null auto_increment,
        version bigint not null,
        name varchar(255) not null,
        primary key (id)
    ) ENGINE=InnoDB

    create table author1_book1 (
        author1_books_id bigint not null,
        book1_id bigint
    ) ENGINE=InnoDB

    create table book1 (
        id bigint not null auto_increment,
        version bigint not null,
        title varchar(255) not null,
        primary key (id)
    ) ENGINE=InnoDB

   alter table author1_book1 
        add constraint FKksyhp1yvpfv9q95v6kmil42tx 
        foreign key (book1_id) 
        references book1 (id)
    
    alter table author1_book1 
        add constraint FKg5h2pe3in61cnibdkr1h99yrd 
        foreign key (author1_books_id) 
        references author1 (id)

*/