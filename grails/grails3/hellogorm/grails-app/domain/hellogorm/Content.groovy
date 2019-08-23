package hellogorm

class Content {

	String author

}


/*


GORM supports inheritance both from abstract base classes and 
concrete persistent GORM entities. For example:

	class Content {
	     String author
	}

	class BlogEntry extends Content {
	    URL url
	}

	class PodCast extends Content {
	    byte[] audioStream
	}

In the above example we have a parent Content class and then various 
child classes with more specific behaviour.

At the database level GORM by default uses table-per-hierarchy mapping 
with a discriminator column called class so the parent class (Content) 
and its subclasses (BlogEntry, Book etc.), share the same table.

Table-per-hierarchy mapping has a down side in that you cannot have 
non-nullable properties with inheritance mapping. An alternative is 
to use table-per-subclass which can be enabled with the ORM DSL

However, excessive use of inheritance and table-per-subclass can 
result in poor query performance due to the use of outer join queries. 
In general our advice is if you’re going to use inheritance, don’t abuse 
it and don’t make your inheritance hierarchy too deep.


SQL DDL:

    
    create table content (
        id bigint not null auto_increment,
        version bigint not null,
        author varchar(255) not null,
        class varchar(255) not null,
        url varchar(255),
        audio_stream tinyblob,
        primary key (id)
    ) ENGINE=InnoDB


*/
