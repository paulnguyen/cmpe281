package hellogorm

class Person2 {

    String name
    Person2 parent

    static belongsTo = [ supervisor: Person2 ]
    static mappedBy = [ supervisor: "none", parent: "none" ]
    static constraints = { supervisor nullable: true }

}


/*

SQL DDL:


    create table person2 (
        id bigint not null auto_increment,
        version bigint not null,
        name varchar(255) not null,
        parent_id bigint,
        supervisor_id bigint,
        primary key (id)
    ) ENGINE=InnoDB



*/