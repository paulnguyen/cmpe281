
package api ;

import gumball.* ;
import java.util.concurrent.BlockingQueue;

public class OrderBatchJob implements Runnable {

	protected BlockingQueue<String> blockingQueue ;
	private GumballMachine machine = GumballMachine.getInstance() ;

	public OrderBatchJob(BlockingQueue<String> queue) {
		this.blockingQueue = queue;
	}

	@Override
	public void run() {
		while (true) {
			try {
				String data = blockingQueue.take();
				System.out.println(Thread.currentThread().getName() + " Processed Order: " + data);
				machine.placeOrder() ;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}