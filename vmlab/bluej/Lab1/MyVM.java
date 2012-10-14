package Lab1;

import java.net.URL;
import com.vmware.vim25.mo.Folder;
import com.vmware.vim25.mo.InventoryNavigator;
import com.vmware.vim25.mo.ServiceInstance;
import com.vmware.vim25.mo.Task;
import com.vmware.vim25.mo.VirtualMachine;

/**
 * Write a description of class MyVM here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MyVM
{
    // instance variables 
    private String vmname ;
    private ServiceInstance si ;
    private VirtualMachine vm ;

    /**
     * Constructor for objects of class MyVM
     */
    public MyVM( String vmname ) 
    {
        // initialise instance variables
        try {
            // your code here
        } catch ( Exception e ) 
        { System.out.println( e.toString() ) ; }

    }

    /**
     * Destructor for objects of class MyVM
     */
    protected void finalize() throws Throwable
    {
       // your code here
    } 

    /**
     * Power On the Virtual Machine
     */
    public void powerOn() 
    {
        try {
              // your code here
        } catch ( Exception e ) 
        { System.out.println( e.toString() ) ; }
    }

    /**
     * Power Off the Virtual Machine
     */
    public void powerOff() 
    {
        try {
             // your code here
        } catch ( Exception e ) 
        { System.out.println( e.toString() ) ; }
    }

     /**
     * Reset the Virtual Machine
     */

    public void reset() 
    {
        try {
              // your code here
        } catch ( Exception e ) 
        { System.out.println( e.toString() ) ; }
    }


     /**
     * Suspend the Virtual Machine
     */
 
    public void suspend() 
    {
        try {
              // your code here
        } catch ( Exception e ) 
        { System.out.println( e.toString() ) ; }
    }

}
