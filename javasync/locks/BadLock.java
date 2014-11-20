
import java.util.concurrent.* ;

public class BadLock
{
    private Semaphore mutex  ;   
    
    public BadLock()
    {
        mutex = new Semaphore(1) ;
    }

    public void Acquire() throws Exception
    {
        mutex.acquire() ;
    }

    public void Release() throws Exception
    {
        mutex.release() ;
    }

}
