
//import java.util.concurrent.Semaphore ;

public class Lock
{
    private String lockName ;
    private Semaphore mutex  ;   
    private String heldBy ;
    
    public Lock()
    {
        mutex = new Semaphore(1) ;
        heldBy = "no one!" ;
        lockName = this.toString() ;
    }

    public void Acquire() throws Exception
    {
       System.out.println( Thread.currentThread().getName() + " requesting lock: " + lockName ) ;
       if ( heldBy.equals(Thread.currentThread().getName()) )
       {
            System.out.println( heldBy + " already has lock: " + lockName ) ;
            return ;
        }
        mutex.acquire() ;
        synchronized( heldBy )
        {
            heldBy = Thread.currentThread().getName() ;
        }
        System.out.println( heldBy + " has lock: " + lockName ) ;
    }

    public void Release() throws Exception
    {
        if ( heldBy.equals(Thread.currentThread().getName()) )
        {
            System.out.println( heldBy + " releasing lock: " + lockName ) ;
            synchronized( heldBy )
            {
                heldBy = "no one!" ;
            }
            mutex.release() ;
            System.out.println( Thread.currentThread().getName() + " released lock: " + lockName ) ;
        }
        else
        {
            System.out.println( "Invalid lock request by thread: " + Thread.currentThread().getName() ) ;
            System.out.println( "Lock: " + lockName + " is currently held by " + heldBy ) ;            
        }
    }

}
