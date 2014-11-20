
import java.util.* ;

public class BoundedBuffer
{
    private int max ;
    private Condition full  ;     
    private Condition empty ; 
    private Lock mutex ;  
    private ArrayList<String> buffer ;

    public BoundedBuffer() 
    {
        this(5) ;   
    }
    
    public BoundedBuffer(int max)
    {
        this.max = max ;
        mutex = new Lock() ; 
        empty = new Condition(mutex) ; 
        full = new Condition(mutex) ; 
        buffer = new ArrayList<String>() ;
    }
    
    public void write(String data) throws Exception
    {
        mutex.Acquire() ;      
        while ( buffer.size() == max )
            empty.Wait() ;
        buffer.add( data ) ;
        full.Signal() ;
        mutex.Release() ;
    }
    
    public String read() throws Exception
    {
        mutex.Acquire() ;
        while ( buffer.isEmpty() )
            full.Wait() ;
        String data = buffer.remove(0) ;
        empty.Signal() ;
        mutex.Release() ;
        return data ;
    }

}
