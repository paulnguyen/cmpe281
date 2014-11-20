
public class Robot
{
    private Lock        mutex ;
    private Condition   noHumansInDanger ;  // no humans in danger
    private Condition   noOrders ;          // no orders in effect
    private Condition   noDanger ;          // not in danger
    private boolean     flagHumanInDanger = false ;
    private boolean     flagOrderFromHuman = false ;
    private boolean     flagSelfInDanger = false ;

    public Robot()
    {
        mutex = new Lock() ;
        noHumansInDanger = new Condition(mutex) ;
        noOrders = new Condition(mutex) ;
        noDanger = new Condition(mutex) ;
    }

    public boolean isSelfInDanger() throws Exception
    {   boolean result = false ;
        mutex.Acquire() ;
        result = flagSelfInDanger ;
        mutex.Release() ;
        return result ; 
    }  

    public boolean isHumanInDanger() throws Exception
    {   boolean result = false ;
        mutex.Acquire() ;
        result = flagHumanInDanger ;
        mutex.Release() ;
        return result ; 
    }  

    public boolean hasOrderFromHuman() throws Exception
    {   boolean result = false ;
        mutex.Acquire() ;
        result = flagOrderFromHuman ;
        mutex.Release() ;
        return result ; 
    }  

    public void humansInDangerEvent() throws Exception
    {
        mutex.Acquire() ;
        System.out.println( "Humans in DANGER!!!!" ) ; 
        flagHumanInDanger = true ;
        mutex.Release() ;
        for ( int i=1; i<=10; i++ )
        {
            System.out.println( "Saving Humans.... #" + i ) ;   
            Thread.currentThread().sleep(1000);
        }
        mutex.Acquire() ;
        flagHumanInDanger = false ;
        noHumansInDanger.Signal() ;
        mutex.Release() ;
        System.out.println( "Safety of Human Secured!!!!" ) ;        
    }
    
    public void selfInDangerAction() throws Exception
    {
        for ( int i=1; i<=10; i++ )
        {
            mutex.Acquire() ;
            while ( flagHumanInDanger )
            {
                noHumansInDanger.Wait() ;
            }
            while ( flagOrderFromHuman )
            {
                noOrders.Wait() ;
            }
            mutex.Release() ;
            System.out.println( "Walking away from danger...#" + i ) ;
            Thread.currentThread().sleep(1000);
        }
        mutex.Acquire() ;
        flagSelfInDanger = false ;
        noDanger.Signal() ;
        mutex.Release() ;
        System.out.println( "Safety At Last!!!" ) ;        
    }
    

    public void walkIntoDangerOrder() throws Exception
    {
        mutex.Acquire() ;
        flagOrderFromHuman = true ;
        mutex.Release() ;
        for ( int i=1; i<=10; i++ )
        {
            mutex.Acquire() ;
            while ( flagHumanInDanger )
                noHumansInDanger.Wait() ;
            mutex.Release() ;
            System.out.println( "Walking into danger...#" + i ) ;
            Thread.currentThread().sleep(1000);
        }
        mutex.Acquire() ;
        flagSelfInDanger = true ;
        flagOrderFromHuman = false ;
        noOrders.Signal() ;
        mutex.Release() ;
        System.out.println( "IN DANGER!!!! IN DANGER!!!!" ) ;
    }
    
}
