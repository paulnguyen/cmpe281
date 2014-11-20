
import java.util.concurrent.Semaphore ;

public class Condition
{
    private Lock theLock ;
    private Semaphore theCondition ;
    private int numWaiters ;

    public Condition(Lock lck)
    {
        this.theLock = lck ;
        this.theCondition = new Semaphore(0) ;
        this.numWaiters = 0 ;
    }

    public void Wait()
    {   // note, must be holding lock already
        try {
            numWaiters++ ;
            theLock.Release() ;
            theCondition.acquire() ;
            theLock.Acquire() ;
        } catch (Exception e) {}
    }
    
    public void Signal()
    {   // note, must be holding lock already
        try {
            if ( numWaiters > 0 )
            {
                numWaiters-- ;
                theCondition.release() ;
            }
        } catch (Exception e) {}
    }
    
    public void Broadcast()
    {   // note, must be holding lock already
        try {
            while ( numWaiters > 0 )
            {
                numWaiters-- ;
                theCondition.release() ;
            }
        } catch (Exception e) {}   
    }
}
