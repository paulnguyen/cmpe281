
public class RobotSemaphore
{
    private Semaphore   mutex ;
    private Semaphore   NoHumansInDanger ;  
    private Semaphore   NoExecuteOrders ;        
    private Semaphore   NoSelfInDanger ;          
    private boolean     flagSelfInDanger = false ;
    

    public RobotSemaphore()
    {
        mutex = new Semaphore(1) ;
        NoHumansInDanger = new Semaphore(1) ;
        NoExecuteOrders = new Semaphore(1) ;
        NoSelfInDanger = new Semaphore(1) ;
    }

    public boolean isSelfInDanger() throws Exception
    {   boolean result = false ;
        mutex.P() ;
        result = flagSelfInDanger ;
        mutex.V() ;
        return result ; 
    }  

    public void humansInDangerEvent() throws Exception
    {
        NoHumansInDanger.P() ;
        System.out.println( "Humans in DANGER!!!!" ) ;  
        for ( int i = 1; i<=10; i++ )
        {
            System.out.println( "Saving Humans.... #" + i ) ;   
            Thread.currentThread().sleep(1000);
        }
        System.out.println( "Safety of Humans Secured!!!!" ) ;        
        NoHumansInDanger.V() ;
    }
    
    public void selfInDangerAction() throws Exception
    {
        NoSelfInDanger.P() ;
        for ( int i = 1; i<=10; i++ )
        {
            NoHumansInDanger.P() ;
            NoExecuteOrders.P() ;
            System.out.println( "Walking away from danger...#" + i ) ;
            Thread.currentThread().sleep(1000);
            NoExecuteOrders.V() ;            
            NoHumansInDanger.V() ;
        }
        mutex.P() ;
        flagSelfInDanger = false ;
        mutex.V() ;
        NoSelfInDanger.V() ;
        System.out.println( "Safety At Last!!!" ) ;        
    }
    

    public void walkIntoDangerOrder() throws Exception
    {
        NoExecuteOrders.P() ;
        for ( int i = 1; i<=10; i++ )
        {
            NoHumansInDanger.P() ;
            System.out.println( "Walking into danger...#" + i ) ;
            Thread.currentThread().sleep(1000);
            NoHumansInDanger.V() ;
        }
        mutex.P() ;
        flagSelfInDanger = true ;
        mutex.V() ;
        NoExecuteOrders.V() ;
        System.out.println( "IN DANGER!!!! IN DANGER!!!!" ) ;
    }
    
}
