
public class BankAccount
{
    private double balance ;
    private Lock theLock ;
    private Condition sufficientFunds ;

    public BankAccount()
    {
        balance = 0.0 ;
        theLock = new Lock() ;
        sufficientFunds = new Condition(theLock) ;
    }
    
    public double Withdraw( double amount ) throws Exception
    {   double result ;
        System.out.println(Thread.currentThread() + " withdraw request => " + amount ) ;
        theLock.Acquire() ;
        while ( amount > balance )
            sufficientFunds.Wait() ;
        balance = balance - amount ;
        result = balance ;
        theLock.Release() ;
        System.out.println(Thread.currentThread() + " withdraw request granted => " + amount ) ;
        return result ;
    }
    
    public double Deposit( double amount ) throws Exception
    {   double result ;
        System.out.println(Thread.currentThread() + " deposit request => " + amount ) ;
        theLock.Acquire() ;
        balance = balance + amount ;
        result = balance ;
        theLock.Release() ;
        System.out.println(Thread.currentThread() + " deposit request granted => " + amount ) ;
        return result ;        
    }

    public void SignalFundsAvailable() throws Exception
    {   theLock.Acquire() ;
        sufficientFunds.Signal() ;
        theLock.Release() ;
    }

    public void BroadcastFundsAvailable() throws Exception
    {   theLock.Acquire() ;
        sufficientFunds.Broadcast() ;
        theLock.Release() ;
    }
    
    
}
