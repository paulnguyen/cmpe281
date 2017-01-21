package api ;

import gumball.* ;


public class GumballMachineAPI {

    private GumballMachine machine = GumballMachine.getInstance() ;

    public synchronized void placeOrder() {
        machine.placeOrder() ;
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

}

