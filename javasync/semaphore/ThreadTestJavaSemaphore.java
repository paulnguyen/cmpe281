

public class ThreadTestJavaSemaphore extends Thread
{
    private java.util.concurrent.Semaphore sem ;
    
    public ThreadTestJavaSemaphore( String n, java.util.concurrent.Semaphore s )
    {
        super(n) ;
        this.sem = s ;
    }
    public void run()
    {
        for ( int i=0; i<5; i++)
        {
            try {
                sem.acquire() ;
                System.out.println( Thread.currentThread() + " Running Task # " + i + "..." ) ;
                sleep(5000) ; // simulate busy work... (sleep for 5 seconds)
                sem.release() ;
            }
            catch (Exception e) {
                System.out.println( e.getMessage() ) ;
            }
        }
    }
}

