package api ;

import java.util.ArrayList ;
import java.util.Random ;
import java.util.UUID ;
import java.util.concurrent.ConcurrentHashMap ;

class Order {

	public String id = UUID.randomUUID().toString() ;
	public String location ; 
    public ArrayList<OrderItem> items = new ArrayList<OrderItem>() ;
    public ConcurrentHashMap<String,String> links = new ConcurrentHashMap<String,String>();
    public StarbucksAPI.OrderStatus status ;
    public String message ;
    
}
