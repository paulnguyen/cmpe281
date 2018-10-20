
package api ;

import java.util.concurrent.BlockingQueue;
import java.net.*;
import java.io.*;

public class StarbucksBarista implements Runnable {

	protected BlockingQueue<String> blockingQueue ;

	private Socket clientSocket ;
    private PrintWriter out ;
    private BufferedReader in ;
    private String host = "localhost" ;
    private int port = 8888 ;

	public StarbucksBarista(BlockingQueue<String> queue) {
		this.blockingQueue = queue;
	}

	@Override
	public void run() {
		while (true) {
			try {

				// open socket
				try {
					clientSocket = new Socket( host, port);
       				out = new PrintWriter(clientSocket.getOutputStream(), true);
        			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));	
        		} catch( IOException err ) {
        			System.out.println( err ) ;
        		}

				try { Thread.sleep(5000); } catch ( Exception e ) {}  

				String order_id = blockingQueue.take();
				Order order = StarbucksAPI.getOrder( order_id ) ;

        		if ( order != null && order.status == StarbucksAPI.OrderStatus.PAID ) {
        			
        			out.println( order_id  + " => PAID"  ) ;
        			try { System.out.println( in.readLine() ) ; } catch (IOException err) {} 

					System.out.println(Thread.currentThread().getName() + " Processed Order: " + order_id );

					StarbucksAPI.setOrderStatus( order, StarbucksAPI.OrderStatus.PREPARING ) ;

       				out.println( order_id  + " => PREPARING"  ) ;
        			try { System.out.println( in.readLine() ) ; } catch (IOException err) {} 

					try { Thread.sleep(20000); } catch ( Exception e ) {}  
					StarbucksAPI.setOrderStatus( order, StarbucksAPI.OrderStatus.SERVED ) ;					

       				out.println( order_id  + " => SERVED"  ) ;
        			try { System.out.println( in.readLine() ) ; } catch (IOException err) {} 


					try { Thread.sleep(10000); } catch ( Exception e ) {}  
					StarbucksAPI.setOrderStatus( order, StarbucksAPI.OrderStatus.COLLECTED ) ;	

       				out.println( order_id  + " => COLLECTED"  ) ;
        			try { System.out.println( in.readLine() ) ; } catch (IOException err) {} 

					try {
			        	in.close();
			        	out.close();
			        	clientSocket.close();
					} catch ( IOException err ) {
						System.out.println( err ) ;
					}        			

				}
				else {
					blockingQueue.put( order_id ) ;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}