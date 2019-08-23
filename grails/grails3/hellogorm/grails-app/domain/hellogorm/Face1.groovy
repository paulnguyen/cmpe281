package hellogorm

class Face1 {

    Nose1 nose

    static constraints = {
    }

}

/*

Bidirectional many-to-one relationship from Face to Nose:

	class Face {
	    Nose nose
	}

	class Nose {
	    static belongsTo = [face:Face]
	}

Saves and deletes will cascade from Face to the associated Nose.

Grails Console Example:

	import hellogorm.*

	new Face1(nose:new Nose1()).save()
	new Nose1(face:new Face1()).save() // will cause an error (Face doesn't "belong to" Nose!)

	def f = Face1.get(1)
	f.delete() // both Face and Nose deleted

SQL DML -- new Face1(nose:new Nose1()).save()

	Hibernate: 

	    insert 
	    into
	        nose1
	        (id, version) 
	    values
	        (null, ?)

	Hibernate: 

	    insert 
	    into
	        face1
	        (id, version, nose_id) 
	    values
	        (null, ?, ?)

SQL DML -- new Face1(nose:new Nose1()).save()

	Hibernate: 
	    insert 
	    into
	        nose1
	        (id, version) 
	    values
	        (null, ?)

    java.lang.NullPointerException
	... 

SQL DDL:

    create table face1 (
        id bigint not null auto_increment,
        version bigint not null,
        nose_id bigint not null,
        primary key (id)
    ) ENGINE=InnoDB

    create table nose1 (
        id bigint not null auto_increment,
        version bigint not null,
        primary key (id)
    ) ENGINE=InnoDB

    alter table face1 
        add constraint FKrjcn3uqf3hjlr15vgfd0m3dar 
        foreign key (nose_id) 
        references nose1 (id)	

*/