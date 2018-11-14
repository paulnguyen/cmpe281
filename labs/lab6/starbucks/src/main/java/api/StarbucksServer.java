package api ;

import org.restlet.*;
import org.restlet.data.Protocol;
import org.restlet.routing.Router;

public class StarbucksServer extends Application {

    public static void main(String[] args) throws Exception {
        Component server = new Component() ;
        server.getServers().add(Protocol.HTTP, 9090) ;
        server.getDefaultHost().attach(new StarbucksServer()) ;
        server.start() ;
        StarbucksAPI.startOrderProcessor() ;
    }

    @Override
    public Restlet createInboundRoot() {
        Router router = new Router(getContext()) ;
        router.attach( "/v1/starbucks/order/{order_id}", OrderResource.class ) ;        
        router.attach( "/v1/starbucks/order/{order_id}/pay", PaymentResource.class ) ;        
        router.attach( "/v1/starbucks/order", OrderResource.class ) ;        
        router.attach( "/v1/starbucks/orders", OrdersResource.class ) ;        
        return router;
    }


}


/*

POST    /order
        Create a new order, and upon success, 
        receive a Location header specifying the new order’s URI.

GET     /order/{order_id}
        Request the current state of the order specified by the URI.

PUT     /order/{order_id}
        Update an order at the given URI with new information, 
        providing the full representation.

DELETE  /order/{order_id}
        Logically remove the order identified by the given URI.

POST    /order/{order_id}/pay
        Process payment for the order.

GET     /orders
        Get list of Open Orders        

*/

        