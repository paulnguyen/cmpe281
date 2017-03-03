package hellogorm

class Airport1 {

	    static hasMany = [flights: Flight1]
	    static mappedBy = [flights: "departureAirport"]

	    //static hasMany = [arrivals: Flight1]
	    //static mappedBy = [arrivals: "destinationAirport"]

}


/*


If you have two properties of the same type on the many side of a one-to-many 
you have to use mappedBy to specify which the collection is mapped:

	class Airport {
	    static hasMany = [flights: Flight]
	    static mappedBy = [flights: "departureAirport"]
	}

	class Flight {
	    Airport departureAirport
	    Airport destinationAirport
	}


SQL DDL:
    
    create table airport1 (
        id bigint not null auto_increment,
        version bigint not null,
        primary key (id)
    ) ENGINE=InnoDB

    create table flight1 (
        id bigint not null auto_increment,
        version bigint not null,
        departure_airport_id bigint not null,
        destination_airport_id bigint not null,
        primary key (id)
    ) ENGINE=InnoDB    

    alter table flight1 
        add constraint FKeu9g8xrxtf8kxgnddximu4juy 
        foreign key (departure_airport_id) 
        references airport1 (id)
    
    alter table flight1 
        add constraint FKla6ipbh3p9drdv7pmfix637dl 
        foreign key (destination_airport_id) 
        references airport1 (id)

*/