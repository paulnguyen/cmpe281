
import java.util.concurrent.locks.* ;

public class JavaLockTestThread extends Thread
{

   private ReentrantLock lock = new ReentrantLock() ;

    public JavaLockTestThread(ReentrantLock lck)
    {
        this.lock = lck ;
    }
   
    public void run()
    {
        try {
            System.out.println( Thread.currentThread().getName() + " START! " ) ;
            sleep(1000) ; // sleep for 5 seconds
            System.out.println( Thread.currentThread().getName() + " Lock Request #1... [held by current thread? "+lock.isHeldByCurrentThread()+"]" ) ;
            lock.tryLock() ;
            System.out.println( Thread.currentThread().getName() + " Holding lock... hold count = " + lock.getHoldCount() + " [held by current thread? "+lock.isHeldByCurrentThread()+"]" ) ;
            sleep (1000) ; // sleep for 1 second
            System.out.println( Thread.currentThread().getName() + " Lock Request #2... [held by current thread? "+lock.isHeldByCurrentThread()+"]" ) ;
            lock.tryLock() ;
            System.out.println( Thread.currentThread().getName() + " Holding lock... hold count = " + lock.getHoldCount() + " [held by current thread? "+lock.isHeldByCurrentThread()+"]" ) ;
            sleep (1000) ; // sleep for 1 second
            System.out.println( Thread.currentThread().getName() + " Lock Release #1... [held by current thread? "+lock.isHeldByCurrentThread()+"]" ) ;
            lock.unlock() ;
            System.out.println( Thread.currentThread().getName() + " Released lock... hold count = " + lock.getHoldCount() + " [held by current thread? "+lock.isHeldByCurrentThread()+"]" ) ;
            sleep (1000) ; // sleep for 1 second
            System.out.println( Thread.currentThread().getName() + " Lock Release #2... [held by current thread? "+lock.isHeldByCurrentThread()+"]" ) ;
            lock.unlock() ;
            System.out.println( Thread.currentThread().getName() + " Released lock... hold count = " + lock.getHoldCount()+" [held by current thread? "+lock.isHeldByCurrentThread()+"]" ) ;
            System.out.println( Thread.currentThread().getName() + " DONE! " ) ;
        } catch ( Exception e )
        {
            System.out.println( e ) ;   
        }
    }

}
