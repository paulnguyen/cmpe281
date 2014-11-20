

public class ProducerThread extends Thread
{
    private BoundedBuffer buffer ;
    
    public ProducerThread( String name, BoundedBuffer buf )
    {   
        super(name) ;
        this.buffer = buf ;
    }
    
    public void run()
    {
        try {
            for (int num=0; num < 10; num++) {
                String product = "Coke #" + num ;
                System.out.println("Producer " + Thread.currentThread() + " produced => " + product ) ;
                buffer.write(product) ;
            }
        } catch ( Exception e ) {
            System.out.println( e.getMessage() ) ;
        }
    }
    
}
