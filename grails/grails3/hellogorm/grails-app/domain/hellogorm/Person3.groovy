package hellogorm

class Person3 {

	    static hasMany = [nicknames: String]

	    static mapping = {
	       nicknames joinTable: [name: 'bunch_o_nicknames',
	                           key: 'person_id',
	                           column: 'nickname',
	                           type: "text"]
	    }

}


/*



As well as associations between different domain classes, GORM also supports 
mapping of basic collection types. For example, the following class creates 
a nicknames association that is a Set of String instances:

	class Person {
	    static hasMany = [nicknames: String]
	}

GORM will map an association like the above using a join table. You can alter 
various aspects of how the join table is mapped using the joinTable argument:

	class Person {

	    static hasMany = [nicknames: String]

	    static mapping = {
	       nicknames joinTable: [name: 'bunch_o_nicknames',
	                           key: 'person_id',
	                           column: 'nickname',
	                           type: "text"]
	    }
	}

SQL DDL:

    create table person3 (
        id bigint not null auto_increment,
        version bigint not null,
        primary key (id)
    ) ENGINE=InnoDB

    create table bunch_o_nicknames (
        person_id bigint not null,
        nickname varchar(255)
    ) ENGINE=InnoDB

    alter table bunch_o_nicknames 
        add constraint FK72jj89mj403j1yfi3rdhgnmi5 
        foreign key (person_id) 
        references person3 (id)

*/