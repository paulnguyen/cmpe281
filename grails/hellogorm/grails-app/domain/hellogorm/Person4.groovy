package hellogorm

class Person4 {

	    Address4 homeAddress
	    Address4 workAddress
	    static embedded = ['homeAddress', 'workAddress']

}


/*

As well as associations, GORM supports the notion of composition. 
In this case instead of mapping classes onto separate tables a 
class can be "embedded" within the current table. For example:

	class Person {
	    Address homeAddress
	    Address workAddress
	    static embedded = ['homeAddress', 'workAddress']
	}

	class Address {
	    String number
	    String code
	}

SQL DDL:

    create table person4 (
        id bigint not null auto_increment,
        version bigint not null,
        home_address_code varchar(255) not null,
        home_address_number varchar(255) not null,
        work_address_code varchar(255) not null,
        work_address_number varchar(255) not null,
        primary key (id)
    ) ENGINE=InnoDB

    create table address4 (
        id bigint not null auto_increment,
        version bigint not null,
        code varchar(255) not null,
        number varchar(255) not null,
        primary key (id)
    ) ENGINE=InnoDB

NOTE:

 	If you define the Address class in a separate Groovy file in the grails-app/domain 
 	directory you will also get an address table. If you don’t want this to happen use 
 	Groovy’s ability to define multiple classes per file and include the Address class 
 	below the Person class in the grails-app/domain/Person.groovy file 

*/