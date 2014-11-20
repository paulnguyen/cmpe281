
public class LockTestThread extends Thread
{

    private Lock lock ;

    public LockTestThread(Lock lck)
    {
        this.lock = lck ;
    }

    public void run()
    {
        try {
            sleep(5000) ; // sleep for 5 seconds
            lock.Acquire() ;
            sleep (1000) ; // sleep for 1 second
            lock.Acquire() ;
            sleep (1000) ; // sleep for 1 second
            lock.Release() ;
            sleep (1000) ; // sleep for 1 second
            lock.Release() ;
            System.out.println( Thread.currentThread().getName() + " DONE! " ) ;
        } catch ( Exception e )
        {
            System.out.println( e.getMessage() ) ;   
        }
    }
}
