package api ;

import org.json.* ;
import org.restlet.representation.* ;
import org.restlet.ext.json.* ;
import org.restlet.resource.* ;
import org.restlet.ext.jackson.* ;
import org.restlet.data.Tag ;
import org.restlet.data.Form ;
import org.restlet.data.Header ;
import org.restlet.data.Digest ;
import org.restlet.util.Series ;
import org.restlet.ext.crypto.DigestUtils ;
import java.io.IOException ;

public class OrderResource extends ServerResource {

    @Get
    public Representation get_action() throws JSONException {

        
        Series<Header> headers = (Series<Header>) getRequest().getAttributes().get("org.restlet.http.headers");
        if ( headers != null ) {
            String etag = headers.getFirstValue("If-None-Match") ;
            System.out.println( "HEADERS: " + headers.getNames() ) ;
            System.out.println( "ETAG: " + etag ) ;            
        }
        

        String order_id = getAttribute("order_id") ;
        Order order = StarbucksAPI.getOrder( order_id ) ;

        if ( order_id == null || order_id.equals("") ) {

            setStatus( org.restlet.data.Status.CLIENT_ERROR_NOT_FOUND ) ;
            api.Status api = new api.Status() ;
            api.status = "error" ;
            api.message = "Order not found." ;

            return new JacksonRepresentation<api.Status>(api) ;
        }
        else {
            Order existing_order = StarbucksAPI.getOrder( order_id ) ;
            if ( order_id == null || order_id.equals("")  || existing_order == null ) {
                setStatus( org.restlet.data.Status.CLIENT_ERROR_NOT_FOUND ) ;
                api.Status api = new api.Status() ;
                api.status = "error" ;
                api.message = "Order not found." ;
                return new JacksonRepresentation<api.Status>(api) ;
            }                
            else {
                Representation result = new JacksonRepresentation<Order>(order) ;
                try { 
                    System.out.println( "Get Text: " + result.getText() ) ;
                    String  hash = DigestUtils.toMd5 ( result.getText() ) ;
                    System.out.println( "Get Hash: " + hash ) ;
                    result.setTag( new Tag( hash ) ) ;
                    return result ;
                }
                catch ( IOException e ) {
                    setStatus( org.restlet.data.Status.SERVER_ERROR_INTERNAL ) ;
                    api.Status api = new api.Status() ;
                    api.status = "error" ;
                    api.message = "Server Error, Try Again Later." ;
                    return new JacksonRepresentation<api.Status>(api) ;
                }
            }
        }
    }


    @Post
    public Representation post_action (Representation rep) throws IOException {

        JacksonRepresentation<Order> orderRep = new JacksonRepresentation<Order> ( rep, Order.class ) ;

        Order order = orderRep.getObject() ;
        StarbucksAPI.setOrderStatus( order, getReference().toString(), StarbucksAPI.OrderStatus.PLACED ) ;
        StarbucksAPI.placeOrder( order.id, order ) ;

        Representation result = new JacksonRepresentation<Order>(order) ;
        try { 
                System.out.println( "Text: " + result.getText() ) ;
                String  hash = DigestUtils.toMd5 ( result.getText() ) ;
                result.setTag( new Tag( hash ) ) ;
                return result ;
        }
        catch ( IOException e ) {
                setStatus( org.restlet.data.Status.SERVER_ERROR_INTERNAL ) ;
                api.Status api = new api.Status() ;
                api.status = "error" ;
                api.message = "Server Error, Try Again Later." ;
                return new JacksonRepresentation<api.Status>(api) ;
        }
    }


   @Put
    public Representation put_action (Representation rep) throws IOException {

        JacksonRepresentation<Order> orderRep = new JacksonRepresentation<Order> ( rep, Order.class ) ;
        Order order = orderRep.getObject() ;

        String order_id = getAttribute("order_id") ;
        Order existing_order = StarbucksAPI.getOrder( order_id ) ;

        if ( order_id == null || order_id.equals("")  || existing_order == null ) {

            setStatus( org.restlet.data.Status.CLIENT_ERROR_NOT_FOUND ) ;
            api.Status api = new api.Status() ;
            api.status = "error" ;
            api.message = "Order not found." ;

            return new JacksonRepresentation<api.Status>(api) ;

        }                
        else if ( existing_order != null && existing_order.status != StarbucksAPI.OrderStatus.PLACED ) {

            setStatus( org.restlet.data.Status.CLIENT_ERROR_PRECONDITION_FAILED ) ;
            api.Status api = new api.Status() ;
            api.status = "error" ;
            api.message = "Order Update Rejected." ;

            return new JacksonRepresentation<api.Status>(api) ;
        }
        else {

            StarbucksAPI.setOrderStatus( order, getReference().toString(), StarbucksAPI.OrderStatus.PLACED ) ;
            order.id = existing_order.id ;
            StarbucksAPI.updateOrder( order.id, order ) ;  
            Representation result = new JacksonRepresentation<Order>(order) ;
            try { 
                    System.out.println( "Text: " + result.getText() ) ;
                    String  hash = DigestUtils.toMd5 ( result.getText() ) ;
                    result.setTag( new Tag( hash ) ) ;
                    return result ;
            }
            catch ( IOException e ) {
                    setStatus( org.restlet.data.Status.SERVER_ERROR_INTERNAL ) ;
                    api.Status api = new api.Status() ;
                    api.status = "error" ;
                    api.message = "Server Error, Try Again Later." ;
                    return new JacksonRepresentation<api.Status>(api) ;
            }
        }
    }

    @Delete
    public Representation delete_action (Representation rep) throws IOException {

        String order_id = getAttribute("order_id") ;
        Order existing_order = StarbucksAPI.getOrder( order_id ) ;
        
        if ( order_id == null || order_id.equals("")  || existing_order == null ) {

            setStatus( org.restlet.data.Status.CLIENT_ERROR_NOT_FOUND ) ;
            api.Status api = new api.Status() ;
            api.status = "error" ;
            api.message = "Order not found." ;

            return new JacksonRepresentation<api.Status>(api) ;

        }        
        else if ( existing_order.status != StarbucksAPI.OrderStatus.PLACED ) {

            setStatus( org.restlet.data.Status.CLIENT_ERROR_PRECONDITION_FAILED ) ;
            api.Status api = new api.Status() ;
            api.status = "error" ;
            api.message = "Order Cancelling Rejected." ;

            return new JacksonRepresentation<api.Status>(api) ;
        }
        else {

            StarbucksAPI.removeOrder( order_id ) ;
            return null ;    
        }

    }

}



