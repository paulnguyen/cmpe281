package api ;

import gumball.* ;
import java.util.concurrent.BlockingQueue ;
import java.util.concurrent.LinkedBlockingQueue ;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;

public class GumballMachineAPI {

    private static BlockingQueue<String> orderQueue = new LinkedBlockingQueue<String>();
    private GumballMachine machine = GumballMachine.getInstance() ;

    public void placeOrder() {
        String data = UUID.randomUUID().toString() ;
        try { GumballMachineAPI.orderQueue.put(data) ; } catch (Exception e) {}
        System.out.println("Order Placed: " + data) ;
    }

    public synchronized int getCount() {
        return machine.getCount() ;
    } 

    public synchronized String toString() {
        return machine.toString() ;
    }

    public synchronized String getStateString() {
        return machine.getStateString() ;
    }

    public static void startBatchJob() {
        OrderBatchJob batchJob = new OrderBatchJob( orderQueue ) ;
        new Thread(batchJob).start();
    }

}

