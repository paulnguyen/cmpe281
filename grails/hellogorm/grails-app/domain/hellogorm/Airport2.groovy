package hellogorm

class Airport2 {

	    static hasMany = [
	    	outboundFlights: Flight2, 
	    	inboundFlights: Flight2
	   	]
	    static mappedBy = [
	    	outboundFlights: "departureAirport",
	        inboundFlights: "destinationAirport"
	    ]
}


/*

If you have multiple collections that map to different properties on the many side,
you have to use mappedBy to specify which the collection is mapped:

	class Airport {
	    static hasMany = [outboundFlights: Flight, inboundFlights: Flight]
	    static mappedBy = [outboundFlights: "departureAirport",
	                       inboundFlights: "destinationAirport"]
	}

	class Flight {
	    Airport departureAirport
	    Airport destinationAirport
	}


SQL DDL:

    create table airport2 (
        id bigint not null auto_increment,
        version bigint not null,
        primary key (id)
    ) ENGINE=InnoDB
    
    create table flight2 (
        id bigint not null auto_increment,
        version bigint not null,
        departure_airport_id bigint not null,
        destination_airport_id bigint not null,
        primary key (id)
    ) ENGINE=InnoDB

 	alter table flight2 
        add constraint FKefl4q5lw8dn2tscaxap5admqt 
        foreign key (departure_airport_id) 
        references airport2 (id)
    
    alter table flight2 
        add constraint FKdfk9m4350wtjd0l08h2wx09r6 
        foreign key (destination_airport_id) 
        references airport2 (id)


*/