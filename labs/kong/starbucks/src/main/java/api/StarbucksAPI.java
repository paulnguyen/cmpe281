package api ;

import java.util.concurrent.BlockingQueue ;
import java.util.concurrent.LinkedBlockingQueue ;
import java.util.concurrent.ConcurrentHashMap ;
import java.util.Collection ;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;

public class StarbucksAPI {

    public enum OrderStatus { PLACED, PAID, PREPARING, SERVED, COLLECTED  }

    private static BlockingQueue<String> orderQueue = new LinkedBlockingQueue<String>();
    private static ConcurrentHashMap<String,Order> orders = new ConcurrentHashMap<String,Order>();

    public static void placeOrder(String order_id, Order order) {
        try { 
            StarbucksAPI.orderQueue.put( order_id ) ; 
        } catch (Exception e) {}
        StarbucksAPI.orders.put( order_id, order ) ;
        System.out.println( "Order Placed: " + order_id ) ;
    }

    public static void startOrderProcessor() {
        StarbucksBarista barista = new StarbucksBarista( orderQueue ) ;
        new Thread(barista).start();
    }

    public static void updateOrder(String key, Order order) {
        StarbucksAPI.orders.replace( key, order ) ;
    }

    public static Order getOrder(String key) {
        return StarbucksAPI.orders.get( key ) ;
    }

    public static void removeOrder(String key) {
        StarbucksAPI.orders.remove( key ) ;
        StarbucksAPI.orderQueue.remove( key ) ;
    }

    public static void setOrderStatus( Order order, String URI, OrderStatus status ) {
        switch ( status ) {
            case PLACED:
                order.status = OrderStatus.PLACED ;
                order.message = "Order has been placed." ;
                order.links.put ("order", URI+"/"+order.id ) ;
                order.links.put ("payment",URI+"/"+order.id+"/pay" ) ;
            break;
                    
            case PAID:
                order.status = StarbucksAPI.OrderStatus.PAID ;
                order.message = "Payment Accepted." ;
                order.links.remove ( "payment" ) ;
            break;                        
        }
    }

    public static void setOrderStatus( Order order, OrderStatus status ) {
        switch ( status ) {
            case PREPARING: 
                order.status = StarbucksAPI.OrderStatus.PREPARING ;
                order.message = "Order preparations in progress." ;
                break;

            case SERVED: 
                order.status = StarbucksAPI.OrderStatus.SERVED ;
                order.message = "Order served, wating for Customer pickup." ;                   
                break;

            case COLLECTED: 
                order.status = StarbucksAPI.OrderStatus.COLLECTED ;
                order.message = "Order retrived by Customer." ;     
                break;   
        }
    }


    public static Collection<Order> getOrders() {
        return orders.values() ;
    }

}


