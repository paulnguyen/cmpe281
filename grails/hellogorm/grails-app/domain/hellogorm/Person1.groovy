package hellogorm

class Person1 {

    String name
    Person1 parent

    static belongsTo = [ supervisor: Person1 ]
    static constraints = { supervisor nullable: true }

}

/*

SQL DDL:

    create table person1 (
        id bigint not null auto_increment,
        version bigint not null,
        name varchar(255) not null,
        parent_id bigint,
        supervisor_id bigint,
        primary key (id)
    ) ENGINE=InnoDB

    alter table person1 
        add constraint FKobdoeqn8gmk375bctc0iv4t5b 
        foreign key (parent_id) 
        references person1 (id)

    alter table person1 
        add constraint FKi5fx4b0gkxsddd6547ts14ux8 
        foreign key (supervisor_id) 
        references person1 (id)

*/