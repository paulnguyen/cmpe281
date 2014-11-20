
public class ConsumerThread extends Thread
{

   private BoundedBuffer buffer ;
    
    public ConsumerThread( String name, BoundedBuffer buf )
    {   
        super(name) ;
        this.buffer = buf ;
    }
    
    public void run()
    {
        try {
            for (int num=0; num < 5; num++) {
                String product ;
                product = buffer.read() ;
                System.out.println("Consumer " + Thread.currentThread() + " consumed => " + product ) ;
                sleep(2000) ;
            }
        } catch ( Exception e ) {
            System.out.println( e.getMessage() ) ;
        }
    }

}
