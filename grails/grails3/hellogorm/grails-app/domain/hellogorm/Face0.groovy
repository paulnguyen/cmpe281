package hellogorm

class Face0 {

	Nose0 nose

    static constraints = {
    }
    	
}

/*

Unidirectional many-to-one relationship from Face to Nose:

	class Face {
	    Nose nose
	}

	class Nose {
	}


SQL DDL:

    create table face0 (
        id bigint not null auto_increment,
        version bigint not null,
        nose_id bigint not null,
        primary key (id)
    ) ENGINE=InnoDB

    create table nose0 (
        id bigint not null auto_increment,
        version bigint not null,
        primary key (id)
    ) ENGINE=InnoDB

    alter table face0 
        add constraint FKf5jvj4r696ee6sennfdc36vuh 
        foreign key (nose_id) 
        references nose0 (id)

*/