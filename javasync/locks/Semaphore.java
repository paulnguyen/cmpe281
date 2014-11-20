
import java.util.* ;

public class Semaphore
{
    private int x ;
    
    public Semaphore()          { init(0) ; }
    public Semaphore( int cnt ) { init(cnt) ; }
    
    public synchronized void init( int cnt ) 
    { 
        x = cnt ; 
    }

    public synchronized void P() throws Exception
    {
       // decrement and block if result is < 0
       x-- ;
       if ( x < 0 ) 
       {
            System.out.println( Thread.currentThread() + " Waiting..." ) ;
            this.wait() ;
            System.out.println( Thread.currentThread() + " Block and wait complete!" ) ;
       }
       else
       {
            System.out.println( Thread.currentThread() +" No need to wait, okay to proceed!" ) ;
       }
    }
    
    public synchronized void V() throws Exception
    {
       // increment and wake up a waiting P if any   
       if ( x < 0 )
       {
            x++ ;
            this.notify() ;
            System.out.println( Thread.currentThread() + " Woke up a waiter." ) ;
        }
        else
        {
            x++ ;
            System.out.println( Thread.currentThread() + " Signal sent. No waiters!" ) ;
        }
    }
    
    public void Wait() throws Exception { P() ; }
    public void Signal() throws Exception { V() ; }
    public void acquire() throws Exception { P() ; }
    public void release() throws Exception { V() ; }
    
}
