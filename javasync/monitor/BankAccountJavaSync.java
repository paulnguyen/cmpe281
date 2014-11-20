
public class BankAccountJavaSync
{
    private double balance ;

    public BankAccountJavaSync()
    {
        balance = 0.0 ;
    }
    
    public synchronized double Withdraw( double amount ) throws Exception
    {   double result ;
        System.out.println(Thread.currentThread() + " withdraw request => " + amount ) ;
        while ( amount > balance )
            wait() ;
        balance = balance - amount ;
        result = balance ;
        System.out.println(Thread.currentThread() + " withdraw request granted => " + amount ) ;
        return result ;
    }
    
    public synchronized double Deposit( double amount ) throws Exception
    {   double result ;
        System.out.println(Thread.currentThread() + " deposit request => " + amount ) ;
        balance = balance + amount ;
        result = balance ;
        notifyAll() ;
        System.out.println(Thread.currentThread() + " deposit request granted => " + amount ) ;
        return result ;        
    }

    
}
