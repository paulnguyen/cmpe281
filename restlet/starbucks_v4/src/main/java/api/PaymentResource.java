package api ;

import org.json.* ;
import org.restlet.representation.* ;
import org.restlet.ext.json.* ;
import org.restlet.resource.* ;
import org.restlet.ext.jackson.* ;

import java.io.IOException ;

public class PaymentResource extends ServerResource {

    @Post
    public Representation post_action (Representation rep) throws IOException {

        String order_id = getAttribute("order_id") ;
        Order order = StarbucksAPI.getOrder( order_id ) ;
        
        if ( order == null ) {
            setStatus( org.restlet.data.Status.CLIENT_ERROR_NOT_FOUND ) ;
            api.Status api = new api.Status() ;
            api.status = "error" ;
            api.message = "Order Not Found" ;
            return new JacksonRepresentation<api.Status>(api) ;
        }
        if ( order != null && order.status != StarbucksAPI.OrderStatus.PLACED ) {
            setStatus( org.restlet.data.Status.CLIENT_ERROR_PRECONDITION_FAILED ) ;
            api.Status api = new api.Status() ;
            api.status = "error" ;
            api.message = "Order Payment Rejected" ;
            return new JacksonRepresentation<api.Status>(api) ;
        }
        else {
            order.id = order_id ;
            StarbucksAPI.setOrderStatus( order, getReference().toString(), StarbucksAPI.OrderStatus.PAID ) ;
            StarbucksAPI.updateOrder( order.id, order ) ; 
            return new JacksonRepresentation<Order>(order) ;           
        }

    }


}


