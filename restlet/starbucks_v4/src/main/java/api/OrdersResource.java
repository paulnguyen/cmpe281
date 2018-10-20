package api ;

import org.json.* ;
import org.restlet.representation.* ;
import org.restlet.ext.json.* ;
import org.restlet.resource.* ;
import org.restlet.ext.jackson.* ;

import java.io.IOException ;
import java.util.Collection ;

public class OrdersResource extends ServerResource {

    @Get
    public Representation get_action (Representation rep) throws IOException {
        Collection<Order> orders = StarbucksAPI.getOrders() ;
        return new JacksonRepresentation<Collection<Order>>(orders) ;           
    }


}


