import java.util.concurrent.Semaphore ;
import java.util.* ;

public class BoundedBuffer
{
    private int max ;
    private Semaphore full  ;     
    private Semaphore empty ; 
    private Semaphore mutex ;  
    private ArrayList<String> buffer ;

    public BoundedBuffer() 
    {
        this(5) ;   
    }
    
    public BoundedBuffer(int max)
    {
        this.max = max ;
        full = new Semaphore( 0 ) ; // initially not full
        empty = new Semaphore( max ) ; // initially empty
        mutex = new Semaphore( 1 ) ; // mutex 
        buffer = new ArrayList<String>() ;
    }
    
    public void write(String data) throws Exception
    {
        empty.acquire() ;
        mutex.acquire() ;
        buffer.add( data ) ;
        mutex.release() ;
        full.release() ;
    }

    public String read() throws Exception
    {
        full.acquire() ;
        mutex.acquire() ;
        String data = buffer.remove(0) ;
        mutex.release() ;
        empty.release() ;
        return data ;
    }

}
